package com.yl.campus.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.yl.campus.R;
import com.yl.campus.adapter.DateAndWeekAdapter;
import com.yl.campus.model.Curriculum;
import com.yl.campus.presenter.CurriculumPresenter;
import com.yl.campus.util.LoadingUtil;
import com.yl.campus.util.ToastUtil;
import com.yl.campus.view.CurriculumView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class CurriculumActivity extends BaseActivity implements CurriculumView {

    @ViewById
    GridView dateAndWeekGrid;
    @ViewById
    FrameLayout courseLayout;
    private int courseWidth;
    private int courseHeight;
    private CurriculumPresenter presenter = new CurriculumPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_curriculum;
    }

    @Override
    protected String getToolbarTitle() {
        return getString(R.string.week_num_text);
    }

    @Override
    public void showProgressDialog() {
        LoadingUtil.onLoad(this, "正在加载...");
    }

    @Override
    public void hideProgressDialog() {
        LoadingUtil.endLoad();
    }

    @AfterViews
    @Override
    public void initCurriculum() {
        // 显示日期和星期
        dateAndWeekGrid.setAdapter(new DateAndWeekAdapter());
        // 获取屏幕宽度
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        // 根据屏幕宽度设置一节课的宽高
        courseWidth = screenWidth / 8;
        courseHeight = courseWidth * 3 / 2;
        // 填充表头节次
        courseLayout.removeAllViews();
        for (int i = 1; i < 12; i++) {
            ViewHolder holder = new ViewHolder(this);
            holder.courseNameText.setText(getString(R.string.course_num_text, i));
            holder.teacherNameText.setVisibility(View.GONE);
            holder.coursePositionText.setVisibility(View.GONE);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    courseWidth, courseHeight);
            params.setMargins(0, courseHeight * (i - 1), 0, 0);
            params.gravity = Gravity.TOP | Gravity.START;
            holder.courseView.setLayoutParams(params);
            courseLayout.addView(holder.courseView);
        }
        // 获取课程表数据
        presenter.getCurriculumData();
    }

    @Override
    public void showCourseContent(Curriculum curriculum) {
        titleText.setText(getString(R.string.week_num_text, curriculum.weekNum));
        for (Curriculum.Course course : curriculum.courses) {
            ViewHolder holder = new ViewHolder(this);
            holder.courseNameText.setText(course.courseName);
            holder.teacherNameText.setText(course.teacherName);
            holder.coursePositionText.setText(course.classroom);
            holder.courseView.setBackgroundColor(Color.parseColor(course.bgColor));
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(courseWidth,
                    courseHeight * (course.endLesson - course.startLesson + 1));
            params.setMargins((course.weekday + 1) * courseWidth,
                    (course.startLesson - 1) * courseHeight, 0, 0);
            holder.courseView.setLayoutParams(params);
            courseLayout.addView(holder.courseView);
        }
    }

    @Override
    public void onLoadFailed() {
        ToastUtil.showToast(this, "加载失败", 0);
        finish();
    }

    private class ViewHolder {
        View courseView;
        TextView courseNameText;
        TextView teacherNameText;
        TextView coursePositionText;

        ViewHolder(Context context) {
            courseView = LayoutInflater.from(context)
                    .inflate(R.layout.item_course, courseLayout, false);
            courseNameText = (TextView)
                    courseView.findViewById(R.id.courseNameText);
            teacherNameText = (TextView)
                    courseView.findViewById(R.id.teacherNameText);
            coursePositionText = (TextView)
                    courseView.findViewById(R.id.coursePositionText);
        }
    }
}
