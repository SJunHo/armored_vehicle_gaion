package kr.gaion.armoredVehicle.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;


@Entity
public class Sda {
    @Id
    @Column(name="SDAID")
    private String sdaId;

    @Column(name="SDANM")
    private String sdaName;

    @Column(name="DIVS")
    private String divs;

    @Column(name="RGSTNO")
    private String registerNo;

    @Column(name="SN")
    private String serialNo;

    @Column(name="ACODT")
    private String AcquisitionDate;

    @Column(name="MFDT")
    private String manufactureDate;

    @Column(name="CRTDT")
    private Date creationDate;

    @Column(name="CRTOR")
    private String constructor;

    @Column(name="MDFCDT")
    private Date aiPredict;

    @Column(name="MDFR")
    private String modificationDate;

    @Column(name="BRGD")
    private String brgd;

    @Column(name="BN")
    private String bn;

    @Column(name="RGSTDT")
    private String registrationDate;

    @Column(name="SDATYPE")
    private String sdaType;

    @Column(name="DIVSCODE")
    private String divsCode;

    @Column(name="BRGDBNCODE")
    private String brgdBnCode;
}
