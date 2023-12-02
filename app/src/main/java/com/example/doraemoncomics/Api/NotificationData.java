package com.example.doraemoncomics.Api;


import java.io.Serializable;

public class NotificationData implements Serializable {
    private String to;

    private NotificationPayload data;

    public NotificationData(String to, NotificationPayload data) {
        this.to = to;
        this.data = data;
    }

    public NotificationData() {
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public NotificationPayload getData() {
        return data;
    }

    public void setData(NotificationPayload data) {
        this.data = data;
    }

    public static class NotificationPayload {
        private String title;

        private String body;

        public NotificationPayload(String title, String body) {
            this.title = title;
            this.body = body;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }
}
