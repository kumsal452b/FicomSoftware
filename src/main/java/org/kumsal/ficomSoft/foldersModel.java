package org.kumsal.ficomSoft;

import com.jfoenix.controls.JFXButton;

public class foldersModel {

    private String sira;

    private String destisno;

    private String yuklemeSaati;

    private String yuktarihi;

    private JFXButton sil;

    private JFXButton desgistir;

    public foldersModel(String sira, String destisno, String yuktarihi,String yuklemeSaati,JFXButton sil, JFXButton desgistir) {
        this.sira = sira;
        this.destisno = destisno;
        this.yuklemeSaati = yuklemeSaati;
        this.yuktarihi = yuktarihi;
        this.sil = sil;
        this.desgistir = desgistir;
    }

    public String getSira() {
        return sira;
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

    public String getYuklemeSaati() {
        return yuklemeSaati;
    }

    public void setYuklemeSaati(String yuklemeSaati) {
        this.yuklemeSaati = yuklemeSaati;
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
