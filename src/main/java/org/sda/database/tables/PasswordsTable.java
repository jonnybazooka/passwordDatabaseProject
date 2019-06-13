package org.sda.database.tables;

import org.sda.encryption.Encoder;
import org.sda.encryption.ROT13;
import org.sda.encryption.ROT18;
import org.sda.encryption.ROT47;

public class PasswordsTable {

    private int id;
    private String title;
    private String desc;
    private String email;
    private String url;
    private String password;
    private Rot rot;
    private String key;

    private PasswordsTable(PasswordTableBuilder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.desc = builder.desc;
        this.email = builder.email;
        this.url = builder.url;
        this.password = builder.password;
        this.rot = builder.rot;
        this.key = builder.key;
    }

    public static class PasswordTableBuilder {
        private int id;
        private String title;
        private String desc;
        private String email;
        private String url;
        private String password;
        private Rot rot;
        private String key;

        public PasswordTableBuilder(int id, String title, String password, Rot rot, String key) {
            this.id = id;
            this.title = title;
            this.password = password;
            this.rot = rot;
            this.key = key;
            this.desc = " ";
            this.email = " ";
            this.url = " ";
        }

        public PasswordTableBuilder setDesc(String desc) {
            this.desc = desc;
            return this;
        }

        public PasswordTableBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public PasswordTableBuilder url(String url) {
            this.url = url;
            return this;
        }

        public PasswordsTable build() {
            return new PasswordsTable(this);
        }
    }

    public void showEncryptedPassword() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(" : ").append(title);
        if (!desc.equals(" ") && !desc.equals("")) {
            sb.append(", ").append(desc);
        }
        if(!email.equals(" ") && !email.equals("")) {
            sb.append(", Email: ").append(email);
        }
        if(!url.equals(" ") && !url.equals("")) {
            sb.append(", URL: ").append(url);
        }
        sb.append(", PASSWORD: ").append(password);
        sb.append(", ").append(rot);
        System.out.println(sb.toString());
    }

    public void showDecryptedPassword() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(" : ").append(title);
        if (!desc.equals(" ") && !desc.equals("")) {
            sb.append(", ").append(desc);
        }
        if(!email.equals(" ") && !email.equals("")) {
            sb.append(", Email: ").append(email);
        }
        if(!url.equals(" ") && !url.equals("")) {
            sb.append(", URL: ").append(url);
        }

        Encoder encoder;
        if (rot.equals(Rot.ROT13)) {
            encoder = new ROT13();
        } else if (rot.equals(Rot.ROT18)) {
            encoder = new ROT18();
        } else {
            encoder = new ROT47();
        }
        sb.append(", PASSWORD: ").append(encoder.decrypt(password.toCharArray()));
        sb.append(", ").append(rot);
        System.out.println(sb.toString());
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getEmail() {
        return email;
    }

    public String getUrl() {
        return url;
    }

    public String getPassword() {
        return password;
    }

    public Rot getRot() {
        return rot;
    }

    public String getKey() {
        return key;
    }
}
