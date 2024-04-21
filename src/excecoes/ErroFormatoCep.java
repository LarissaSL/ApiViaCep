package excecoes;

public class ErroFormatoCep extends RuntimeException{
        private String mensagem;

        public ErroFormatoCep(String mensagem) {
            this.mensagem = mensagem;
        }

    public String getMessage() {
        return this.mensagem;
    }
}
