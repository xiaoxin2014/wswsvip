package com.wdhhr.wswsvipnew.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.base.BaseFragment;
import com.wdhhr.wswsvipnew.constant.HomeContants;
import com.wdhhr.wswsvipnew.constant.ShopSearchConstants;
import com.wdhhr.wswsvipnew.fragment.Home_GoodsFragment;
import com.wdhhr.wswsvipnew.fragment.SearchDialogFragment;
import com.wdhhr.wswsvipnew.fragment.SearchListFragment;
import com.wdhhr.wswsvipnew.model.dataBase.SearchKeyBean;

import org.litepal.crud.DataSupport;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import butterknife.BindView;

import static com.wdhhr.wswsvipnew.utils.StatusBarUtil.StatusBarLightMode;

public class ShopSearchActivity extends BaseActivity {


    @BindView(R.id.edit_search_content)
    EditText mEditSearch;
    @BindView(R.id.tv_search)
    TextView mTvSearch;
    @BindView(R.id.tv_title_back)
    TextView mTvBack;
    @BindView(R.id.tv_search_clear)
    TextView mTvClear;


    private SearchListFragment mSearchListFragment;
    private SearchDialogFragment mSearchDialogFragment;
    private final String TAG = ShopSearchActivity.class.getSimpleName();
    private BaseFragment mLastFragment;
    private Home_GoodsFragment mHome_goodsFragment;
    private String mstrSearchContent;
    private boolean mIsSingle;

    @Override
    protected int setViewId() {
        return R.layout.activity_shop_search;
    }

    @Override
    protected void init() {

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        StatusBarLightMode(this);
        mEditSearch.setHint(R.string.search_shop);
        mTvSearch.setText(R.string.search);

        if (getIntent().getExtras() != null) {
            mIsSingle = getIntent().getExtras().getBoolean("isSingle", false);
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Fragment 处理
        mSearchListFragment = new SearchListFragment();
        mSearchDialogFragment = new SearchDialogFragment();
        transaction.add(R.id.frame, mSearchDialogFragment);

        if (mIsSingle) {
            mLastFragment = mSearchDialogFragment;
        } else {
            transaction.hide(mSearchDialogFragment);
            transaction.add(R.id.frame, mSearchListFragment);
            mLastFragment = mSearchListFragment;
        }

        transaction.commit();

    }


    @Override
    protected void initEvent() {
        mEditSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                changePage(mSearchDialogFragment);
            }
        });

        mEditSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mTvClear.setVisibility(View.VISIBLE);
                    mstrSearchContent = s.toString();

                } else {
                    mTvClear.setVisibility(View.GONE);
                    mstrSearchContent = null;
                    changePage(mSearchDialogFragment);
                }
            }
        });

        mEditSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search(mstrSearchContent);
                }
                return false;
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {
        Fragment currentFrag = null;

        switch (view.getId()) {
            case R.id.tv_title_back:
                onBackPressed();
                break;
            case R.id.tv_search:
                search(mstrSearchContent);
                break;
            case R.id.edit_search_content:
                if (mstrSearchContent != null) {
                    return;
                }
                currentFrag = mSearchDialogFragment;
                break;
            case R.id.tv_search_clear:
                mTvClear.setVisibility(View.GONE);
                mEditSearch.setText("");
                mstrSearchContent = null;
                currentFrag = mSearchDialogFragment;
                break;
        }
        if (currentFrag != null) {
            changePage(currentFrag);
        }

    }

    private void changePage(Fragment currentFrag) {
        if (currentFrag != null && currentFrag != mLastFragment) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(mLastFragment);
            transaction.show(currentFrag);
            mLastFragment = (BaseFragment) currentFrag;
            transaction.commit();
        }
    }

    @Subscriber(tag = ShopSearchConstants.EVENT_START_SEARCH)
    private void search(String keyWord) {

        // 判断是否外部传入
        if (!TextUtils.equals(keyWord, mstrSearchContent)) {
            mstrSearchContent = keyWord;
            mEditSearch.setText(keyWord);
            mEditSearch.setSelection(mEditSearch.getText().length());
        }
        // 开始搜索
        if (mstrSearchContent == null) {
            showLongToast("请先输入搜索内容");
            return;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(mLastFragment);
        if (mHome_goodsFragment != null) {
            transaction.remove(mHome_goodsFragment);
        }

        mHome_goodsFragment = Home_GoodsFragment.newInstance(mstrSearchContent, HomeContants.MODE_SEARCH_BY_NAME_SHOP);
        transaction.add(R.id.frame, mHome_goodsFragment);
        mLastFragment = mHome_goodsFragment;
        transaction.commit();

        // 保存搜索关键字
        DataSupport.deleteAll(SearchKeyBean.class, "keyWord = ?", mstrSearchContent);
        SearchKeyBean searchKeyBean = new SearchKeyBean();
        searchKeyBean.setKeyWord(mstrSearchContent);
        searchKeyBean.save();
        // 发出历史关键字变化通知
        EventBus.getDefault().post(0, ShopSearchConstants.EVENT_UPDATE_HISTORYKEY);
    }

    @Override
    public void onBackPressed() {
        if (!mIsSingle && mLastFragment != mSearchListFragment) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mEditSearch.getWindowToken(), 0);
            mEditSearch.setText("");
            mstrSearchContent = null;
            changePage(mSearchListFragment);
        } else {
            finish();
        }
    }
}
