package org.kumsal.ficomSoft.AdapterModelClass;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Label;

import java.time.LocalDate;

public class load_model {
    private String count;
    private JFXDatePicker time;
    private JFXTextField sayi;
    private JFXTextField konu;
    private JFXTextField adet;
    private JFXDatePicker evrakTarihi;
    private JFXDatePicker imhaTarihi;

    public load_model(String count,JFXTextField sayi, JFXTextField konu, JFXTextField adet,JFXDatePicker time, JFXDatePicker evrakTarihi, JFXDatePicker imhaTarihi) {
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

    public JFXDatePicker getTime() {
        return time;
    }

    public void setTime(JFXDatePicker time) {
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

    public JFXDatePicker getEvrakTarihi() {
        return evrakTarihi;
    }

    public void setEvrakTarihi(JFXDatePicker evrakTarihi) {
        this.evrakTarihi = evrakTarihi;
    }

    public JFXDatePicker getImhaTarihi() {
        return imhaTarihi;
    }

    public void setImhaTarihi(JFXDatePicker imhaTarihi) {
        this.imhaTarihi = imhaTarihi;
    }
}
