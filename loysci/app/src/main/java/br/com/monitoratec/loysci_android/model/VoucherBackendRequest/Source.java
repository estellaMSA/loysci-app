package br.com.monitoratec.loysci_android.model.VoucherBackendRequest;

import org.simpleframework.xml.Element;

public class Source {

        @Element(name = "RequestorID")
        private RequestorID requestorID;

        Source() {
            this.requestorID = new RequestorID();
        }

        public RequestorID getRequestorID() {
            return requestorID;
        }

    }