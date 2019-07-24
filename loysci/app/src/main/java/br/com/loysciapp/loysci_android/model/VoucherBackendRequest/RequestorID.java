package br.com.loysciapp.loysci_android.model.VoucherBackendRequest;

import org.simpleframework.xml.Attribute;

public class RequestorID {
            @Attribute(name = "Type")
            private String Type = "5";

            @Attribute(name = "ID")
            private String ID = "206048";

            @Attribute(name = "MessagePassword")
            private String MessagePassword = "1574ade742b3050f2343f7a3237f1411";

            @Attribute(name = "ID_Context")
            private String ID_Context = "Movida";

            public String getType() {
                return Type;
            }

            public void setType(String type) {
                Type = type;
            }

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public String getMessagePassword() {
                return MessagePassword;
            }

            public void setMessagePassword(String messagePassword) {
                MessagePassword = messagePassword;
            }

            public String getID_Context() {
                return ID_Context;
            }

            public void setID_Context(String ID_Context) {
                this.ID_Context = ID_Context;
            }
        }