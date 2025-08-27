package Logging;

import java.util.logging.Logger;

public class Logging {

    private Logger logger = Logger.getLogger(Logging.class.getName());

    public Logging() {
        logger.info("Iniciando o sistema de gerenciamento de biblioteca");
    }
    public void logModoTeste(boolean modoTeste) {
        if (modoTeste) {
            logger.info("O sistema está rodando no modo de teste");
        } else {
            logger.info("O sistema está rodando no modo normal");
        }
    }
}
