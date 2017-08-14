package com.burakgon.starview;

import android.content.Context;
import android.graphics.Point;
import android.media.Image;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by burakgon-user on 8/11/2017.
 */

public class StarView extends RelativeLayout {
    private Animation anim; //top right to left bottom animation
    LayoutInflater mInflater;
    View view;
    private Handler mHandler;
    private int heightOfScreen,widthOfSreen; //screen sizes
    private ArrayList<ImageView> topStarList,rightStarList; //dynamically created imageviews (star)
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        topStarList = new ArrayList();
        rightStarList = new ArrayList();
        getScreenSize(); //getting screen sizes
        mHandler = new Handler(); // init handler for repeating thread
        starViewCreater(); //we create imageViews which contain stars
        startTopStarRepeatingTask(); //starting  top side star animation
        startRightStarRepeatingTask(); //starting right side star animation

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        //we stop our thread so we wont use phone resources for nothing
        stopTopStarRepeatingTask();
        stopRightStarRepeatingTask();
    }

    //we create random animation time and start random image star animation for top side
    Runnable mTopStarThread = new Runnable() {
        @Override
        public void run() {
            try {
                Random rand = new Random();
                int starNum = rand.nextInt(topStarList.size()); //getting random star
                anim.setDuration(rand.nextInt(1000)+1000); //random animation duration
                topStarList.get(starNum).startAnimation(anim); //starting animation

            } finally {
                mHandler.postDelayed(mTopStarThread, 2000); //starting thread again for loop
            }
        }
    };

    //starting top star task
    void startTopStarRepeatingTask() {
        mTopStarThread.run();
    }

    //stopping top star task
    void stopTopStarRepeatingTask() {
        mHandler.removeCallbacks(mTopStarThread);
    }

    //we create random animation time and start random image star animation for right side
    Runnable mRightStarThread = new Runnable() {
        @Override
        public void run() {
            try {
                Random rand = new Random();
                int starNum = rand.nextInt(rightStarList.size());
                anim.setDuration(rand.nextInt(1000)+1000);
                rightStarList.get(starNum).startAnimation(anim);

            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler.postDelayed(mRightStarThread, 2000);
            }
        }
    };

    //starting right star task
    void startRightStarRepeatingTask() {
        mRightStarThread.run();
    }

    //stopping right star task
    void stopRightStarRepeatingTask() {
        mHandler.removeCallbacks(mRightStarThread);
    }

    //getting screen size
    private void getScreenSize() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        widthOfSreen = size.x;
        heightOfScreen = size.y;
    }

    public StarView(Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
        init();
    }

    public StarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        init();


    }

    public StarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
        init();


    }

    //initializing views and anims
    private void init(){

        view = mInflater.inflate(R.layout.star_layout, this);
        anim = AnimationUtils.loadAnimation(view.getContext(),R.anim.topright_to_bottomleft);

    }

    /*main part of code
    *we are getting screen size so we can dynamically create our imageviews
    * also we set random star images to imageviews
    * we set coordinates and sizes of imageviews
    * after that we add them into arraylists so we can use them later
    */
    private void starViewCreater(){

        //deciding how many star view we need
        int widthStarCount = widthOfSreen / 100;
        int heightStarCount = heightOfScreen / 100;

        //we create imageviews and add random star images to them for top side
        for (int i = 0 ; i<=widthStarCount ; i++){
            ImageView imageView = new ImageView(view.getContext());

            Random random = new Random();
            int randomStarNumber = random.nextInt(2);

            int randomStar;

            //random star image select
            switch (randomStarNumber){
                case 0 :
                    randomStar = R.drawable.star_one;
                    break;
                case 1:
                    randomStar = R.drawable.star_two;
                    break;
                case 2:
                    randomStar = R.drawable.star_three;
                    break;
                default:
                    randomStar = R.drawable.star_one;
                    break;
            }
            //setting star size
            LayoutParams lp = new LayoutParams(random.nextInt(40)+80,random.nextInt(60)+60);
            imageView.setImageResource(randomStar);
            //setting star position
            imageView.setX(i*100);
            imageView.setY(0);
            imageView.setLayoutParams(lp);
            //we dont want to visible in first place so we set their visibilty to invisible
            imageView.setVisibility(ImageView.INVISIBLE);
            //adding our imageviews to layout
            ((RelativeLayout)view).addView(imageView);
            //we also add them to arraylist
            topStarList.add(imageView);
        }


        //we create imageviews and add random star images to them for right side
        for (int i = 0 ; i<=heightStarCount ; i++){
            ImageView imageView = new ImageView(view.getContext());

            Random random = new Random();
            int randomStarNumber = random.nextInt(2);

            int randomStar;

            //random star image select
            switch (randomStarNumber){
                case 0 :
                    randomStar = R.drawable.star_one;
                    break;
                case 1:
                    randomStar = R.drawable.star_two;
                    break;
                case 2:
                    randomStar = R.drawable.star_three;
                    break;
                default:
                    randomStar = R.drawable.star_one;
                    break;
            }
            //setting star size
            LayoutParams lp = new LayoutParams(random.nextInt(40)+80,random.nextInt(60)+60);
            imageView.setImageResource(randomStar);
            //setting star position
            imageView.setX(widthOfSreen-60);
            imageView.setY(i*100);
            imageView.setLayoutParams(lp);
            //we dont want to visible in first place so we set their visibilty to invisible
            imageView.setVisibility(ImageView.INVISIBLE);
            //adding our imageviews to layout
            ((RelativeLayout)view).addView(imageView);
            //we also add them to arraylist
            rightStarList.add(imageView);

        }
    }
}
