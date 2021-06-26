package com.helper.util;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

import com.helper.callback.Response;

public class AnimationUtil {


    public static void alphaAnimation(View view, int visibility) {
        alphaAnimation(view, visibility, 400, null);
    }

    public static void alphaAnimation(View view, int visibility, int duration) {
        alphaAnimation(view, visibility, duration, null);
    }

    public static void alphaAnimation(View view, int visibility, int duration, Response.AnimatorListener animatorListener) {
        AlphaAnimation alphaAnim;
        if (visibility == View.VISIBLE) {
            alphaAnim = new AlphaAnimation(0.0f, 1.0f);
        } else {
            alphaAnim = new AlphaAnimation(1.0f, 0.0f);
        }
        alphaAnim.setDuration(duration);
//        alphaAnim.setStartOffset(5000);
        alphaAnim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation arg0) {
                view.setVisibility(visibility);
                if (animatorListener != null) {
                    animatorListener.onAnimationEnd(null);
                }
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {

            }

            @Override
            public void onAnimationStart(Animation arg0) {
                if (animatorListener != null) {
                    animatorListener.onAnimationStart(null);
                }
            }

        });
        view.startAnimation(alphaAnim);
    }


    public static void viewExpandAnimation(View view, int fromWidth, int toWidth, Response.AnimatorListener animatorListener) {
        if (view != null) {
            ValueAnimator widthAnimation = ValueAnimator.ofInt(fromWidth, toWidth);
            widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator updatedAnimation) {
                    int val = (Integer) updatedAnimation.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    layoutParams.width = val;
                    view.setLayoutParams(layoutParams);
                    view.requestLayout();
                }
            });

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(3000);
            animatorSet.playTogether(widthAnimation);
            widthAnimation.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    if (animatorListener != null) {
                        animatorListener.onAnimationStart(animation);
                    }
                    view.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.VISIBLE);
                    if (animatorListener != null) {
                        animatorListener.onAnimationEnd(animation);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            view.setVisibility(View.VISIBLE);

            widthAnimation.start();
        }
    }

    public static void viewCollapseAnimation(View view, int fromWidth, int toWidth, Response.AnimatorListener animatorListener) {
        if (view != null) {
            ValueAnimator widthAnimation = ValueAnimator.ofInt(fromWidth, toWidth);
            widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator updatedAnimation) {
                    int val = (Integer) updatedAnimation.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    layoutParams.width = val;
                    view.setLayoutParams(layoutParams);
                    view.requestLayout();
                }
            });
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(3000);
            animatorSet.playTogether(widthAnimation);
            widthAnimation.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    if (animatorListener != null) {
                        animatorListener.onAnimationStart(animation);
                    }
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (animatorListener != null) {
                        animatorListener.onAnimationEnd(animation);
                    }
                    view.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            widthAnimation.start();
        }
    }

    public static void viewIncreaseAnimation(View view, int fromWidth, int toWidth, Response.AnimatorListener animatorListener) {
        if (view != null) {
            ValueAnimator valueAnimator = ValueAnimator.ofInt(fromWidth, toWidth);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator updatedAnimation) {
                    int val = (Integer) updatedAnimation.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    layoutParams.width = val;
                    layoutParams.height = val;
                    view.setLayoutParams(layoutParams);
                    view.requestLayout();
                }
            });
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(100);
            animatorSet.playTogether(valueAnimator);
            valueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    if (animatorListener != null) {
                        animatorListener.onAnimationStart(animation);
                    }
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (animatorListener != null) {
                        animatorListener.onAnimationEnd(animation);
                    }
                    view.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            valueAnimator.start();
        }
    }


    public static Animation slideDown(View llMainView) {
        int height = llMainView.getMeasuredHeight();

        Animation anim = new TranslateAnimation(0, 0, 0, height);
        anim.setDuration(300);
        anim.setInterpolator(new AccelerateInterpolator());
        llMainView.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                llMainView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return anim;
    }

    public static Animation slideUp(View llMainView) {
        int height = llMainView.getMeasuredHeight();

        Animation anim = new TranslateAnimation(0, 0, height, 0);
        anim.setInterpolator(new AccelerateInterpolator());
        anim.setDuration(300);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                llMainView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        llMainView.startAnimation(anim);
        return anim;
    }

    public static void slideLeftToRight(View view) {
        if (view != null) {
            view.setVisibility(View.VISIBLE);
            int parentWidth = ((View) view.getParent()).getMeasuredWidth();
            ValueAnimator widthAnimator = ValueAnimator.ofInt(view.getWidth(), parentWidth);
            widthAnimator.setDuration(200);
            widthAnimator.setInterpolator(new DecelerateInterpolator());
            widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    view.getLayoutParams().width = (int) animation.getAnimatedValue();
                    view.requestLayout();
                }
            });
            widthAnimator.start();
        }
    }

    public static void slideRightToLeft(View view) {
        if (view != null) {
            ValueAnimator widthAnimator = ValueAnimator.ofInt(view.getWidth(), 0);
            widthAnimator.setDuration(100);
            widthAnimator.setInterpolator(new DecelerateInterpolator());
            widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    view.getLayoutParams().width = (int) animation.getAnimatedValue();
                    view.requestLayout();
                }
            });
            widthAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            widthAnimator.start();
        }
    }
}
