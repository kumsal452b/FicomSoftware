package org.kumsal.ficomSoft.AdapterModelClass;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.time.LocalDate;

public class load_model {
    private String count;
    private DatePicker time;
    private JFXTextField sayi;
    private JFXTextField konu;
    private JFXTextField adet;
    private DatePicker evrakTarihi;
    private DatePicker imhaTarihi;

    public load_model(String count,JFXTextField sayi, JFXTextField konu, JFXTextField adet,DatePicker time, DatePicker evrakTarihi, DatePicker imhaTarihi) {
        this.count = count;
        this.time = time;
        this.sayi = sayi;
        this.konu = konu;
        this.adet = adet;
        this.evrakTarihi = evrakTarihi;
        this.imhaTarihi = imhaTarihi;

    }

    public String  getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public DatePicker getTime() {
        return time;
    }

    public void setTime(DatePicker time) {
        this.time = time;
    }

    public JFXTextField getSayi() {
        return sayi;
    }

    public void setSayi(JFXTextField sayi) {
        this.sayi = sayi;
    }

    public JFXTextField getKonu() {
        return konu;
    }

    public void setKonu(JFXTextField konu) {
        this.konu = konu;
    }

    public JFXTextField getAdet() {
        return adet;
    }

    public void setAdet(JFXTextField adet) {
        this.adet = adet;
    }

    public DatePicker getEvrakTarihi() {
        return evrakTarihi;
    }

    public void setEvrakTarihi(DatePicker evrakTarihi) {
        this.evrakTarihi = evrakTarihi;
    }

    public DatePicker getImhaTarihi() {
        return imhaTarihi;
    }

    public void setImhaTarihi(DatePicker imhaTarihi) {
        this.imhaTarihi = imhaTarihi;
    }
}
