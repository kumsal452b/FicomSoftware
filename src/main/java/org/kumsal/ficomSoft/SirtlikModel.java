package org.kumsal.ficomSoft;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

public class SirtlikModel {

    private String destisno;


    private String birimad;


    private String spdkod;


    private String spdkarsilik;


    private String ozelkod;


    private String ozelkarsilik;


    private String klasno;


    private String ktarihi;


    private String aciklama;


    private String yuktarihi;

    private JFXCheckBox ısCheck;


    public SirtlikModel(String destisno, String birimad, String spdkod, String spdkarsilik, String ozelkod, String ozelkarsilik, String klasno, String ktarihi, String aciklama, String yuktarihi, JFXCheckBox isCheck) {
        this.destisno = destisno;
        this.birimad = birimad;
        this.spdkod = spdkod;
        this.spdkarsilik = spdkarsilik;
        this.ozelkod = ozelkod;
        this.ozelkarsilik = ozelkarsilik;
        this.klasno = klasno;
        this.ktarihi = ktarihi;
        this.aciklama = aciklama;
        this.yuktarihi = yuktarihi;
        this.ısCheck=isCheck;
    }

    public JFXCheckBox getIsCheck() {
        return ısCheck;
    }

    public void setIsCheck(JFXCheckBox ısCheck) {
        this.ısCheck = ısCheck;
    }
    

    public String getDestisno() {
        return destisno;
    }

    public void setDestisno(String destisno) {
        this.destisno = destisno;
    }

    public String getBirimad() {
        return birimad;
    }

    public void setBirimad(String birimad) {
        this.birimad = birimad;
    }

    public String getSpdkod() {
        return spdkod;
    }

    public void setSpdkod(String spdkod) {
        this.spdkod = spdkod;
    }

    public String getSpdkarsilik() {
        return spdkarsilik;
    }

    public void setSpdkarsilik(String spdkarsilik) {
        this.spdkarsilik = spdkarsilik;
    }

    public String getOzelkod() {
        return ozelkod;
    }

    public void setOzelkod(String ozelkod) {
        this.ozelkod = ozelkod;
    }

    public String getOzelkarsilik() {
        return ozelkarsilik;
    }

    public void setOzelkarsilik(String ozelkarsilik) {
        this.ozelkarsilik = ozelkarsilik;
    }

    public String getKlasno() {
        return klasno;
    }

    public void setKlasno(String klasno) {
        this.klasno = klasno;
    }

    public String getKtarihi() {
        return ktarihi;
    }

    public void setKtarihi(String ktarihi) {
        this.ktarihi = ktarihi;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getYuktarihi() {
        return yuktarihi;
    }

    public void setYuktarihi(String yuktarihi) {
        this.yuktarihi = yuktarihi;
    }
}
