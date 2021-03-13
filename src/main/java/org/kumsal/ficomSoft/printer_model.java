package org.kumsal.ficomSoft;

import java.time.LocalDate;

public class printer_model {
    private String count;
    private String time;
    private String sayi;
    private String konu;
    private String adet;
    private String evrakTarihi;
    private String imhaTarihi;

    public printer_model(String count, String time, String sayi, String konu, String adet, String evrakTarihi, String imhaTarihi) {
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
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

    public String getEvrakTarihi() {
        return evrakTarihi;
    }

    public void setEvrakTarihi(String evrakTarihi) {
        this.evrakTarihi = evrakTarihi;
    }

    public String getImhaTarihi() {
        return imhaTarihi;
    }

    public void setImhaTarihi(String imhaTarihi) {
        this.imhaTarihi = imhaTarihi;
    }
}
