package progmatic.hegymaszas.modell.messages;

import progmatic.hegymaszas.modell.MyUser;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class DirectMessage extends Message {
    @NotNull
    @ManyToOne
    private MyUser addressee;

    private boolean isRead;


    public DirectMessage() {
    }


    public MyUser getAddressee() {
        return addressee;
    }


    public void setAddressee(MyUser addressee) {
        this.addressee = addressee;
    }


    public boolean isRead() {
        return isRead;
    }


    public void setRead(boolean read) {
        isRead = read;
    }
}
