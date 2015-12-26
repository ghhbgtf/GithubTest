package com.example.atlas.githubtest;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ToxicBakery.viewpager.transforms.CubeInTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //顶部图片广告展示 bar
    private ConvenientBanner convenientBanner;
    //本地图片数组
    private ArrayList<Integer> localImages = new ArrayList<>();
    //下面两项是网络图片数组以及资源
    private List<String> networkImages;
    private String[] images = {"http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg",
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://d.3987.com/sqmy_131219/001.jpg",
            "http://img2.3lian.com/2014/f2/37/d/39.jpg",
            "http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg",
            "http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        editBanner();
    }

    // 开始自动翻页
    @Override
    protected void onResume() {
        super.onResume();
        //开始自动翻页
        convenientBanner.startTurning(5000);
    }

    // 停止自动翻页
    @Override
    protected void onPause() {
        super.onPause();
        //停止翻页
        convenientBanner.stopTurning();
    }

    private void initView() {
        convenientBanner = (ConvenientBanner) findViewById(R.id.convenientBanner);
    }

    private void editBanner() {

        //加载本地图片
        initImageLocal();
        //加载网络图片缓存
        initImageLoader();

        //本地图片控件设置
        CBViewHolderCreator<LocalImageHolderView> cbViewHolderCreator1 = new CBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        };

        //网络图片控件设置
        CBViewHolderCreator<NetworkImageHolderView> cbViewHolderCreator2 = new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        };


//        convenientBanner.setPages(cbViewHolderCreator1, localImages);//给图片展示bar 加载本地资源
        convenientBanner.setPages(cbViewHolderCreator2, networkImages);//给图片展示bar 加载网络资源

        //设置 convenientBanner 的相关属性
        convenientBanner
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})//设置指示器的图片,选中状态和未选中状态两张图片
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);//设置指示器的位置(左边中间右边)
        try {
            convenientBanner.getViewPager().setPageTransformer(true, CubeInTransformer.class.newInstance());//设置图片翻页的效果
        } catch (Exception e) {
        }

    }

    //------------------------------------------------------------------------------------------
    //初始化本地图片
    private void initImageLocal() {
        //本地图片集合
        for (int position = 0; position < 7; position++) {
            //给本地图片数组中添加本地图片资源
            localImages.add(getResId("ic_test_" + position, R.drawable.class));
        }
    }

    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    //------------------------------------------------------------------------------------------
    //初始化网络图片缓存库
    private void initImageLoader() {
        //网络图片例子,结合常用的图片缓存库UIL,你可以根据自己需求自己换其他网络图片库
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_default_adimage)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext()).defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
        //给网络图片数组中添加网络图片资源
        networkImages = Arrays.asList(images);
    }

}
