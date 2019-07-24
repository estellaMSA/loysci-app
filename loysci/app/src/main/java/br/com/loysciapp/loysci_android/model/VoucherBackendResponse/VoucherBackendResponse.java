package br.com.loysciapp.loysci_android.model.VoucherBackendResponse;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "CouponInsRS")
public class VoucherBackendResponse {

    @Attribute(name = "Target")
    private String Target;

    @Attribute(name = "SequenceNmbr")
    private String SequenceNmbr;

    @Attribute(name = "CorrelationID")
    private String CorrelationID;

    @Element(name = "Success", required = false)
    private String Success;

    @Attribute(name = "Version")
    private String Version;

    @Attribute(name = "PrimaryLangID")
    private String PrimaryLangID;

    public String success;

    @Element(name = "CouponRSCore")
    private CouponRSCore CouponRSCore;

    public String getTarget() {
        return Target;
    }

    public void setTarget(String Target) {
        this.Target = Target;
    }

    public String getSequenceNmbr() {
        return SequenceNmbr;
    }

    public void setSequenceNmbr(String SequenceNmbr) {
        this.SequenceNmbr = SequenceNmbr;
    }

    public String getCorrelationID() {
        return CorrelationID;
    }

    public void setCorrelationID(String CorrelationID) {
        this.CorrelationID = CorrelationID;
    }

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String Success) {
        this.Success = Success;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String Version) {
        this.Version = Version;
    }

    public String getPrimaryLangID() {
        return PrimaryLangID;
    }

    public void setPrimaryLangID(String PrimaryLangID) {
        this.PrimaryLangID = PrimaryLangID;
    }

    public CouponRSCore getCouponRSCore() {
        return CouponRSCore;
    }

    public void setCouponRSCore(CouponRSCore CouponRSCore) {
        this.CouponRSCore = CouponRSCore;
    }

    @Override
    public String toString() {
        return "ClassPojo [Target = " + Target + ", SequenceNmbr = " + SequenceNmbr + ", CorrelationID = " + CorrelationID + ", Success = " + Success + ", Version = " + Version + ", PrimaryLangID = " + PrimaryLangID + ", success = " + success + ", CouponRSCore = " + CouponRSCore + "]";
    }
}
	