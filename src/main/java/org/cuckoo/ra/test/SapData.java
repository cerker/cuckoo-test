package org.cuckoo.ra.test;

public class SapData {

    private String rfcHost;
    private String ipAddress;
    private String sapRelease;

    public void setRfcHost( String rfcHost ) {
        this.rfcHost = rfcHost;
    }

    public String getRfcHost() {
        return rfcHost;
    }

    public void setIpAddress( String ipAddress ) {
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setSapRelease( String sapRelease ) {
        this.sapRelease = sapRelease;
    }

    public String getSapRelease() {
        return sapRelease;
    }
}
