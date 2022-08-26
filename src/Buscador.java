
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.xml.rpc.ServiceException;

import CEPService_pkg.CEPService;
import CEPService_pkg.CEPServiceLocator;
import CEPService_pkg.CEPServicePort;

public class Buscador {

	public static void main(String[] args) throws ServiceException, NumberFormatException, IOException {
		BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

		String[] listaBusca;
		int menuSwitch = -1;

		// Cria um campo para Login
		String login = JOptionPane.showInputDialog("Digite o Login:");

		// Cria campo onde o usuario entra com a senha
		JPasswordField password = new JPasswordField(10);
		password.setEchoChar('*');

		// Cria um rótulo para o campo Senha
		JLabel rotulo = new JLabel("Digite a Senha:");

		// Coloca o r�tulo e a caixa de entrada numa JPanel:
		JPanel entUsuario = new JPanel();
		entUsuario.add(rotulo);
		entUsuario.add(password);

		// Mostra o r�tulo e a caixa de entrada de password para o usuario fornecer a
		// senha:
		JOptionPane.showMessageDialog(null, entUsuario, "SENHA", JOptionPane.PLAIN_MESSAGE);

		// Captura a senha:
		String senha = password.getText();

		// Faz a conexço com o serviço
		CEPService servico = new CEPServiceLocator();
		CEPServicePort porta = servico.getCEPServicePort();

		// Cria um campo de funções para usuário
		while (menuSwitch != 3) {
			System.out.println("+++++ BUSCADOR ENDERE�O E CEP +++++");
			System.out.println("Digite 1 para Buscar Endereço pelo CEP");
			System.out.println("Digite 2 para Buscar Endereço por logradouro");
			System.out.println("Digite 3 para Creditar na conta");

			menuSwitch = Integer.parseInt(teclado.readLine());

			switch (menuSwitch) {

			case 1:
				// Cria um campo para preenchimento, conforme solicitado no menu do usuário
				System.out.println("Informe o CEP desejado");
				String cep = teclado.readLine();
				// Recebe a solicitações do CEP e retorna com os dados disponíveis.
				System.out.println(porta.obterLogradouroAuth(cep, login, senha) + "\n");

				break;

			case 2:
				// Cria alguns campos para preenchimento, conforme solicitado no menu do usuário
				System.out.println("Digite o Logradouro");
				String logradouro = teclado.readLine();
				System.out.println("Digite a Cidade");
				String cidade = teclado.readLine();
				System.out.println("Digite o UF");
				String uf = teclado.readLine();

				// Array que recebe o resultado das solicitações e retorna com os dados.
				listaBusca = porta.obterCEPAuth(logradouro, cidade, uf, login, senha);

				// Imprime cada e conta a quantidade de dados recebidos
				for (int i = 0; i < listaBusca.length; i++) {
					System.out.println("\n" + listaBusca[i]);
				}
				// Imprimi o somatório de dados recebidos
				System.out.println("\nTotal de dados obtidos: " + listaBusca.length + "\n");
				break;

			case 3:
				// Imprime Mensagem informativa
				System.out.println("Operação Finalizada");
				break;

			default:
				// Imprime Mensagem informativa
				System.out.println("Opção Incorreta");

			}
		}
	}
}