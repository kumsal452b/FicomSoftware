package org.kumsal.ficomSoft;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

import java.util.ArrayList;

public class LoadedFileModel {

    private String sira;


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


    private JFXButton sil;


    private JFXButton desgistir;

    private JFXButton goster;

    private String prossTime;

    public LoadedFileModel(String prossTime) {
        this.prossTime = prossTime;
    }

    public LoadedFileModel(String sira, String destisno, String birimad, String spdkod, String spdkarsilik, String ozelkod, String ozelkarsilik, String klasno, String ktarihi, String aciklama, String yuktarihi, JFXButton sil, JFXButton desgistir) {
        this.sira = sira;
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
        this.sil = sil;
        this.desgistir = desgistir;
    }

    public LoadedFileModel(JFXButton goster) {
        this.goster = goster;
    }

    public JFXButton getGoster() {
        return goster;
    }

    public void setGoster(JFXButton goster) {
        this.goster = goster;
    }

    public String getSira() {
        return sira;
    }

    public String getProssTime() {
        return prossTime;
    }

    public void setProssTime(String prossTime) {
        this.prossTime = prossTime;
    }

    public void setSira(String sira) {
        this.sira = sira;
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

    public JFXButton getSil() {
        return sil;
    }

    public void setSil(JFXButton sil) {
        this.sil = sil;
    }

    public JFXButton getDesgistir() {
        return desgistir;
    }

    public void setDesgistir(JFXButton desgistir) {
        this.desgistir = desgistir;
    }
}
