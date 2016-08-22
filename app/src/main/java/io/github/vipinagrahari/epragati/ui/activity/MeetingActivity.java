package io.github.vipinagrahari.epragati.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;

import io.github.vipinagrahari.epragati.R;
import io.github.vipinagrahari.epragati.data.model.Meeting;

public class MeetingActivity extends AppCompatActivity {
    final String BULLET_POINT = "\u2022 ";
    final String NEW_LINE = "\n";

    Meeting meeting;
    TextView tvMeetingDate, tvMeetingLocation, tvMeetingTime, tvMeetingPresident, tvMeetingPoints, tvMeetingAttendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);
        meeting = getIntent().getParcelableExtra("meeting");

        tvMeetingDate = (TextView) findViewById(R.id.header_meeting_date);
        tvMeetingLocation = (TextView) findViewById(R.id.tv_meeting_location);
        tvMeetingTime = (TextView) findViewById(R.id.tv_meeting_time);
        tvMeetingPresident = (TextView) findViewById(R.id.tv_meeting_president);
        tvMeetingPoints = (TextView) findViewById(R.id.tv_meeting_points);
        tvMeetingAttendance = (TextView) findViewById(R.id.tv_meeting_attendance);

        tvMeetingDate.setText(meeting.getDate());
        tvMeetingLocation.setText(meeting.getLocation());
        tvMeetingPresident.setText(meeting.getPresident());
        tvMeetingAttendance.setText(meeting.getAttendedBy());
        tvMeetingTime.setText(meeting.getTime());
        tvMeetingPoints.setText(getBulletPointList(meeting.getPoints()));


    }

    private String getBulletPointList(List<String> points) {
        StringBuffer pointsList = new StringBuffer(NEW_LINE);
        for (String point : points) {
            pointsList.append(BULLET_POINT);
            pointsList.append(point);
            pointsList.append(NEW_LINE);
            pointsList.append(NEW_LINE);

        }
        return new String(pointsList);
    }


}
