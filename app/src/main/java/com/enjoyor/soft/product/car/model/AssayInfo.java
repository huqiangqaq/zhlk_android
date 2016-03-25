package com.enjoyor.soft.product.car.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huqiang on 2016/3/7 17:53.
 */
public class AssayInfo implements Parcelable {
    private String mcur_rongzhong;
    private String mcur_water;
    private String mcur_chonghai;
    private String mcur_zhazhi;
    private String mcur_notcomplete;
    private String mcur_color;

    public AssayInfo(String mcur_rongzhong, String mcur_water, String mcur_chonghai, String mcur_zhazhi, String mcur_notcomplete, String mcur_color) {
        this.mcur_rongzhong = mcur_rongzhong;
        this.mcur_water = mcur_water;
        this.mcur_chonghai = mcur_chonghai;
        this.mcur_zhazhi = mcur_zhazhi;
        this.mcur_notcomplete = mcur_notcomplete;
        this.mcur_color = mcur_color;
    }

    public String getMcur_rongzhong() {
        return mcur_rongzhong;
    }

    public void setMcur_rongzhong(String mcur_rongzhong) {
        this.mcur_rongzhong = mcur_rongzhong;
    }

    public String getMcur_water() {
        return mcur_water;
    }

    public void setMcur_water(String mcur_water) {
        this.mcur_water = mcur_water;
    }

    public String getMcur_chonghai() {
        return mcur_chonghai;
    }

    public void setMcur_chonghai(String mcur_chonghai) {
        this.mcur_chonghai = mcur_chonghai;
    }

    public String getMcur_zhazhi() {
        return mcur_zhazhi;
    }

    public void setMcur_zhazhi(String mcur_zhazhi) {
        this.mcur_zhazhi = mcur_zhazhi;
    }

    public String getMcur_notcomplete() {
        return mcur_notcomplete;
    }

    public void setMcur_notcomplete(String mcur_notcomplete) {
        this.mcur_notcomplete = mcur_notcomplete;
    }

    public String getMcur_color() {
        return mcur_color;
    }

    public void setMcur_color(String mcur_color) {
        this.mcur_color = mcur_color;
    }

    @Override
    public String toString() {
        return "AssayInfo{" +
                "mcur_rongzhong='" + mcur_rongzhong + '\'' +
                ", mcur_water='" + mcur_water + '\'' +
                ", mcur_chonghai='" + mcur_chonghai + '\'' +
                ", mcur_zhazhi='" + mcur_zhazhi + '\'' +
                ", mcur_notcomplete='" + mcur_notcomplete + '\'' +
                ", mcur_color='" + mcur_color + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mcur_rongzhong);
        dest.writeString(this.mcur_water);
        dest.writeString(this.mcur_chonghai);
        dest.writeString(this.mcur_zhazhi);
        dest.writeString(this.mcur_notcomplete);
        dest.writeString(this.mcur_color);
    }

    protected AssayInfo(Parcel in) {
        this.mcur_rongzhong = in.readString();
        this.mcur_water = in.readString();
        this.mcur_chonghai = in.readString();
        this.mcur_zhazhi = in.readString();
        this.mcur_notcomplete = in.readString();
        this.mcur_color = in.readString();
    }

    public static final Parcelable.Creator<AssayInfo> CREATOR = new Parcelable.Creator<AssayInfo>() {
        public AssayInfo createFromParcel(Parcel source) {
            return new AssayInfo(source);
        }

        public AssayInfo[] newArray(int size) {
            return new AssayInfo[size];
        }
    };
}
