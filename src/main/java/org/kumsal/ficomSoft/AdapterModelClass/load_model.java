package org.kumsal.ficomSoft.AdapterModelClass;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Label;

import java.time.LocalDate;

public class load_model {
    private String count;
    private LocalDate time;
    private String sayi;
    private String konu;
    private String adet;
    private LocalDate evrakTarihi;
    private LocalDate imhaTarihi;

    public load_model(String count, LocalDate time, String sayi, String konu, String adet, LocalDate evrakTarihi, LocalDate imhaTarihi) {
        this.count = count;
        this.time = time;
        this.sayi = sayi;
        this.konu = konu;
        this.adet = adet;
        this.evrakTarihi = evrakTarihi;
        this.imhaTarihi = imhaTarihi;

    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public String getSayi() {
        return sayi;
    }

    public void setSayi(String sayi) {
        this.sayi = sayi;
    }

    public String getKonu() {
        return konu;
    }

    public void setKonu(String konu) {
        this.konu = konu;
    }

    public String getAdet() {
        return adet;
    }

    public void setAdet(String adet) {
        this.adet = adet;
    }

    public LocalDate getEvrakTarihi() {
        return evrakTarihi;
    }

    public void setEvrakTarihi(LocalDate evrakTarihi) {
        this.evrakTarihi = evrakTarihi;
    }

    public LocalDate getImhaTarihi() {
        return imhaTarihi;
    }

    public void setImhaTarihi(LocalDate imhaTarihi) {
        this.imhaTarihi = imhaTarihi;
    }
}
