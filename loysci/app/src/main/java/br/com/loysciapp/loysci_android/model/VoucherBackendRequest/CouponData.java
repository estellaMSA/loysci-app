package br.com.loysciapp.loysci_android.model.VoucherBackendRequest;

import org.simpleframework.xml.Element;

import br.com.loysciapp.loysci_android.util.Constants;

public class CouponData {

        @Element(name = "CouponsCPF")
        public CouponsCPF couponsCPF;

        CouponData(String Cpf, String grupos) {
            this.couponsCPF = new CouponsCPF(Cpf);
            IniVigencia = System.currentTimeMillis();
            FimVigencia = System.currentTimeMillis() + Constants.SIX_MONTHS;
            Qtde = "1";
            QtdePorCPF = "1";
            DataInicial = System.currentTimeMillis();
            DataFinal = System.currentTimeMillis() + Constants.SIX_MONTHS;
            Grupos = grupos;
        }


        @Element(name = "IniVigencia")
        private long IniVigencia;

        @Element(name = "FimVigencia")
        private long FimVigencia;

        @Element(name = "Qtde")
        private String Qtde;

        @Element(name = "QtdePorCPF")
        private String QtdePorCPF;

        @Element(name = "DataInicial")
        private long DataInicial;

        @Element(name = "DataFinal")
        private long DataFinal;

        @Element(name = "Grupos")
        private String Grupos;

        public long getIniVigencia() {
            return IniVigencia;
        }

        public void setIniVigencia(long iniVigencia) {
            IniVigencia = iniVigencia;
        }

        public long getFimVigencia() {
            return FimVigencia;
        }

        public void setFimVigencia(long fimVigencia) {
            FimVigencia = fimVigencia;
        }

        public String getQtde() {
            return Qtde;
        }

        public void setQtde(String qtde) {
            Qtde = qtde;
        }

        public String getQtdePorCPF() {
            return QtdePorCPF;
        }

        public void setQtdePorCPF(String qtdePorCPF) {
            QtdePorCPF = qtdePorCPF;
        }

        public long getDataInicial() {
            return DataInicial;
        }

        public void setDataInicial(long dataInicial) {
            DataInicial = dataInicial;
        }

        public long getDataFinal() {
            return DataFinal;
        }

        public void setDataFinal(long dataFinal) {
            DataFinal = dataFinal;
        }

        public String getGrupos() {
            return Grupos;
        }

        public void setGrupos(String grupos) {
            Grupos = grupos;
        }

    }