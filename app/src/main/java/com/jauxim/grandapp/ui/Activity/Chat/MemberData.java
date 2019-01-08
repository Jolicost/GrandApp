package com.jauxim.grandapp.ui.Activity.Chat;


class MemberData {
    private String name;
    private String color;
    private String ivProfilePic;

    public MemberData(String name, String color, String ivProfilePic) {
        this.name = name;
        this.color = color;
        this.ivProfilePic = ivProfilePic;
    }

    public MemberData() {
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getIvProfilePic() { return ivProfilePic; }

    @Override
    public String toString() {
        return "MemberData{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", imagePrc='" + ivProfilePic + '\'' +
                '}';
    }
}