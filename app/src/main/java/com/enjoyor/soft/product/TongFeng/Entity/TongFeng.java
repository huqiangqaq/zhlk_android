package com.enjoyor.soft.product.TongFeng.Entity;

/**
 * Created by 83916 on 2015/12/4.
 */
public class TongFeng {

   private int Id;
    private String BarnNo;
    private String tfBarnDevicesNo;
    private String  code;
    private String Status1;
    private String ReMoteControl;
    private String Msg;

    public TongFeng() {
    }

    public TongFeng(int id, String barnNo, String tfBarnDevicesNo, String code, String status1, String reMoteControl, String msg) {
        Id = id;
        BarnNo = barnNo;
        this.tfBarnDevicesNo = tfBarnDevicesNo;
        this.code = code;
        Status1 = status1;
        ReMoteControl = reMoteControl;
        Msg = msg;
    }

    public TongFeng(String barnNo, String tfBarnDevicesNo, String status1, String reMoteControl, String msg) {
        BarnNo = barnNo;
        this.tfBarnDevicesNo = tfBarnDevicesNo;
        Status1 = status1;
        ReMoteControl = reMoteControl;
        Msg = msg;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getBarnNo() {
        return BarnNo;
    }

    public void setBarnNo(String barnNo) {
        BarnNo = barnNo;
    }

    public String getTfBarnDevicesNo() {
        return tfBarnDevicesNo;
    }

    public void setTfBarnDevicesNo(String tfBarnDevicesNo) {
        this.tfBarnDevicesNo = tfBarnDevicesNo;
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

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    @Override
    public String toString() {
        return "TongFeng{" +
                "Id=" + Id +
                ", BarnNo='" + BarnNo + '\'' +
                ", tfBarnDevicesNo='" + tfBarnDevicesNo + '\'' +
                ", code='" + code + '\'' +
                ", Status1='" + Status1 + '\'' +
                ", ReMoteControl='" + ReMoteControl + '\'' +
                ", Msg='" + Msg + '\'' +
                '}';
    }
}
