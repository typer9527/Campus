package com.yl.campus.app.activity;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.yl.campus.R;
import com.yl.campus.app.adapter.DateAndWeekAdapter;
import com.yl.campus.app.model.Curriculum;
import com.yl.campus.app.presenter.CurriculumPresenter;
import com.yl.campus.app.view.CurriculumView;
import com.yl.campus.common.base.BaseMvpActivity;

import butterknife.BindView;

public class CurriculumActivity extends BaseMvpActivity<CurriculumView, CurriculumPresenter> implements CurriculumView {

    @BindView(R.id.dateAndWeekGrid)
    GridView dateAndWeekGrid;
    @BindView(R.id.courseLayout)
    FrameLayout courseLayout;
    private int courseWidth;
    private int courseHeight;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_curriculum;
    }

    @Override
    protected String getDefaultTitle() {
        return getString(R.string.week_num_text);
    }

    @Override
    protected CurriculumPresenter initPresenter() {
        return new CurriculumPresenter();
    }

    @Override
    protected void initView() {
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
        mPresenter.getCurriculumData();
    }

    @Override
    public void showCourseContent(Curriculum curriculum) {
        actionBar.setTitle(getString(R.string.week_num_text, curriculum.weekNum));
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
