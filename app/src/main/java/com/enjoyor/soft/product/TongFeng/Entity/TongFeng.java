package com.enjoyor.soft.product.TongFeng.Entity;

/**
 * Created by 83916 on 2015/12/4.
 */
public class TongFeng {

   private int Id;
    private String tfBarnDevicesNo;
    private String BarnName;
    private String code;
    private String Status1;
    private String ReMoteControl;
    private String BarnNo;

    public TongFeng() {
    }

    public TongFeng(String tfBarnDevicesNo, String tfBarnDevicesName, String status1, String reMoteControl, String barnNo) {
        this.tfBarnDevicesNo = tfBarnDevicesNo;
        this.BarnName = tfBarnDevicesName;
        Status1 = status1;
        ReMoteControl = reMoteControl;
        BarnNo = barnNo;
    }

    public TongFeng(int id, String tfBarnDevicesNo, String barnName, String code, String status1, String reMoteControl, String barnNo) {
        Id = id;
        this.tfBarnDevicesNo = tfBarnDevicesNo;
        BarnName = barnName;
        this.code = code;
        Status1 = status1;
        ReMoteControl = reMoteControl;
        BarnNo = barnNo;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTfBarnDevicesNo() {
        return tfBarnDevicesNo;
    }

    public void setTfBarnDevicesNo(String tfBarnDevicesNo) {
        this.tfBarnDevicesNo = tfBarnDevicesNo;
    }

    public String getBarnName() {
        return BarnName;
    }

    public void setBarnName(String barnName) {
        BarnName = barnName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus1() {
        return Status1;
    }

    public void setStatus1(String status1) {
        Status1 = status1;
    }

    public String getReMoteControl() {
        return ReMoteControl;
    }

    public void setReMoteControl(String reMoteControl) {
        ReMoteControl = reMoteControl;
    }

    public String getBarnNo() {
        return BarnNo;
    }

    public void setBarnNo(String barnNo) {
        BarnNo = barnNo;
    }

    @Override
    public String toString() {
        return "TongFeng{" +
                "Id=" + Id +
                ", tfBarnDevicesNo='" + tfBarnDevicesNo + '\'' +
                ", BarnName='" + BarnName + '\'' +
                ", code='" + code + '\'' +
                ", Status1='" + Status1 + '\'' +
                ", ReMoteControl='" + ReMoteControl + '\'' +
                ", BarnNo='" + BarnNo + '\'' +
                '}';
    }
}
