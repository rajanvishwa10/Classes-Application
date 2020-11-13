package com.example.classesapplication;

public class Notice {
    String Date, NoticeImage, NoticeText;

    public Notice() {
    }

    public Notice(String date, String noticeImage, String noticeText) {
        Date = date;
        NoticeImage = noticeImage;
        NoticeText = noticeText;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getNoticeImage() {
        return NoticeImage;
    }

    public void setNoticeImage(String noticeImage) {
        NoticeImage = noticeImage;
    }

    public String getNoticeText() {
        return NoticeText;
    }

    public void setNoticeText(String noticeText) {
        NoticeText = noticeText;
    }
}
