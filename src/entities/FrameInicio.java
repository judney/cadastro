package entities;



/* Author .......: Judney 
*/ 
//package entities;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import db.CadastroPojo;
import db.ContatoDAO;
//import javax.swing.JFormattedTextField$AbstractFormatter;
import db.DB;




//public class FrameInicio extends JFrame {


public class FrameInicio extends JFrame{



        private static final long serialVersionUID = 1L;
        //public static Object JOptionPane;
        //public static final String JOptionPane = null;
        private JPanel contentPane;
        private JTextField txtLogradouro;
        private JTextField txtNumero;

        private JTextField txtComplemento;
        private JTextField txtBairro;
        private JTextField txtCidade;
        private JTextField txtUf;
        //private JTextField txtCpf;
        private Integer codCli= 0 ; 
        private String opt ;
        private String vrCpf ; 
        
        
        
         
        public  FrameInicio() throws Exception {
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setBounds(60, 100, 800, 600);
                contentPane = new JPanel();
                contentPane.setBackground(Color.CYAN);
                contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                setContentPane(contentPane);
                getContentPane().setLayout(null) ; 
                contentPane.setLayout(null);
                contentPane.paintComponents(getGraphics());


                JLabel lblTitulo = new JLabel("Cadastro de Clientes");
                lblTitulo.setFont(new Font("Lucida Grande", Font.BOLD, 20));
                contentPane.add(lblTitulo);

                lblTitulo.setBounds(293, 20, 400, 16);

                JLabel lblCodigo = new JLabel("Código..: ") ; 
                lblCodigo.setBounds(16, 59, 70, 20);
                contentPane.add(lblCodigo ); 
    

                String numero =null ; 


                JTextField txtCodigo = new JFormattedTextField(new MaskFormatter("#####"));
                txtCodigo.setBounds(76,59, 70,20 );
                txtCodigo.setCaretPosition(0);

                codCli=leNextCodigo() ; 

                txtCodigo.setText(""+codCli );

                txtCodigo.setEnabled(false);
                contentPane.add(txtCodigo); 


                JLabel lblNome = new JLabel("Nome..: ");
                lblNome.setBounds(175, 59, 70, 20);
                contentPane.add(lblNome);

                JTextField txtNome = new JTextField("Nome Cliente ");
                txtNome.addFocusListener(new FocusAdapter() {
                        @Override
                        public void focusLost(FocusEvent e) {
                    String nome = txtNome.getText().trim() ;
                    Integer var = txtNome.getText().trim().length(); 
                    
                    //System.out.println(nome+"||"+var );
                    if ( nome == null || var == 0   )  { 
                        JOptionPane.showMessageDialog( null , "Atenção !!! Nome não pode ser igual a nulo ");
                        txtNome.requestFocus(); 
                    
                        txtNome.requestFocusInWindow(null); 
                    }
                        } 
                       
        }); 
                    


                txtNome.setBounds(228,59, 566,20 );
                txtNome.setCaretPosition(0);


                contentPane.add(txtNome); 

                JLabel lblCep = new JLabel("Cep.......: ");
                lblCep.setBounds(16, 103, 70, 20);
                contentPane.add(lblCep);




                JTextField txtCep = new JFormattedTextField(new MaskFormatter("#####-###"));
                txtCep.addFocusListener(new FocusAdapter() {
                        @Override
                        public void focusLost(FocusEvent e) {


                                 
                                String cep = txtCep.getText();
                            Integer var = txtCep.getText().trim().length(); 
                                String cepAux = cep.replaceAll("-", "");
                            cep=cepAux.trim() ;
                                //System.out.printf("cep : [%s]\n", cep);
                                //String vrcep=null; 
                                if ( cep.equals("") || var == 0 ) { 
                                JOptionPane.showMessageDialog(txtCep, "Cep Nao pode ser igual a Nulo "); 
                                txtCep.requestFocus(); 
                                }
                                else 
                                { 
                                        Endereco endereco=null ;
                                        try {
                                                endereco = veCep( cep);
                                        } catch (Exception e1) {
                                                // TODO Auto-generated catch block
                                                e1.printStackTrace();
                                        } 


                                        String ret = endereco.toString().substring(20,24); 

                                        String varend ="null"; 


                                        if ( ret.equals(varend)  ) 
                                       { 
                                                    txtLogradouro.setText("");
                                                    txtBairro.setText("");
                                                        txtCidade.setText("");
                                                        txtUf.setText("");
                                                String msg = "Cep : "+ cep + " não encontrado , verifique !!!" ;
                                          //  System.out.println( msg ) ; 
                                                JOptionPane.showMessageDialog( null ,msg );
                                                txtCep.requestFocus(); 
                                       }
                                        else
                                           {
                                                 txtLogradouro.setText(endereco.getLogradouro()); 
                                                 txtBairro.setText(endereco.getBairro()); 
                                                 txtCidade.setText(endereco.getLocalidade()); 
                                                 txtUf.setText(endereco.getuf());
                                                //txtNumero.requestFocus();
                                           } 
                                }    
                                

                        }
                });
                txtCep.setBounds(76,103, 84,20 );
                txtCep.setCaretPosition(0);



                contentPane.add(txtCep); 


                JLabel lblLogradouro = new JLabel("Logradouro..:");
                lblLogradouro.setBounds(175, 103, 89, 20);
                contentPane.add(lblLogradouro);

                txtLogradouro = new JTextField("Logradouro");
                txtLogradouro.setCaretPosition(0);
                txtLogradouro.setBounds(264, 103, 413, 20);
                contentPane.add(txtLogradouro);

                JLabel lblNum = new JLabel("Num : ");
                lblNum.setBounds(684, 103, 42, 20);
                contentPane.add(lblNum);

                JFormattedTextField txtNumero = new JFormattedTextField(new MaskFormatter("**********") );
                //JTextField txtNumero = new JTextField("N/A"); 
                txtNumero.setText("N/A");
                txtNumero.addFocusListener(new FocusAdapter() {
                        //public String num;

                        @Override
                        public void focusLost(FocusEvent e) {
                    String Numero = txtNumero.getText(); 
                    Integer var = txtNumero.getText().trim().length();
                    String comp = "" ; 
                        if (  numero == comp  || var == 0  ) { 
                                JOptionPane.showMessageDialog(txtCep, "Numero  Nao pode ser igual a Nulo ");

                                txtNumero.requestFocus();
                            }

                        //System.out.println("Na tela ..: "+ txtNumero.getText() );


                        }

                });  

                txtNumero.setCaretPosition(0);
                txtNumero.setBounds(724, 103, 70, 20);
                contentPane.add(txtNumero);
                //System.out.println("depois da tela ..: "+ txtNumero.getText()); 


                JLabel lblComplemento = new JLabel("Comp....:");
                lblComplemento.setBounds(16, 147, 108, 20);
                contentPane.add(lblComplemento);

                txtComplemento = new JTextField("N/A");
                txtComplemento.setBounds(76, 147, 84, 20);
                txtComplemento.setCaretPosition(0);
                
                contentPane.add(txtComplemento);

                JLabel lblBairro = new JLabel("Bairro.:");
                lblBairro.setBounds(175, 147, 58, 20);
                contentPane.add(lblBairro);

                txtBairro = new JTextField("Bairro");
                txtBairro.setCaretPosition(0);
                txtBairro.setBounds(228, 147, 265, 20);
                contentPane.add(txtBairro);



                JLabel lblCidade = new JLabel("Cidade.:");
                lblCidade.setBounds(494, 147, 51, 20);
                contentPane.add(lblCidade);

                txtCidade = new JTextField("Cidade");
                txtCidade.setCaretPosition(0);
                txtCidade.setBounds(545, 147, 193, 20);
                contentPane.add(txtCidade);


                JLabel lblUf = new JLabel("UF:");
                lblUf.setBounds(739, 147, 20, 20);
                contentPane.add(lblUf);

                txtUf = new JTextField("UF");
                txtUf.addFocusListener(new FocusAdapter() {
                        @Override
                        public void focusLost(FocusEvent e) {
                                Object[] options = { "Confirmar", "Cancelar" }; 

                                int resposta = JOptionPane.showOptionDialog(null, "Confirma a inclusão ? ", "Informação", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                                //System.out.println(resposta ); 

                                if ( resposta==0 )
                                  {
                                     JOptionPane.showMessageDialog(null, "Incluindo dados iniciais do cliente  : "+txtNome.getText());
                                     CadastroPojo cadastro = new CadastroPojo();
                                     
                                     
                                     
                                     //cadastro.setCodigo(Integer.parseInt(txtCodigo.getText()));
                                     cadastro.setCodigo(codCli);
                                     cadastro.setNome(txtNome.getText());
                                     String cep=txtCep.getText();
                                     String cepAux = cep.replaceAll("-", "");
                                         cep=cepAux ;
                                     cadastro.setCep(cep);
                                     cadastro.setLogradouro(txtLogradouro.getText());
                                     //System.out.println ( "TXTNumenro na inclusao : "+txtNumero.getText()   + "||"); 
                                     cadastro.setNumero(txtNumero.getText());
                                     cadastro.setComplemento(txtComplemento.getText());
                                     cadastro.setBairro(txtBairro.getText());
                                     cadastro.setCidade(txtCidade.getText());
                                     cadastro.setUF(txtUf.getText());
                                     
                                     //txtNumero.getText();
                                     

                                     //System.out.println(txtBairro.getText()) ; 
                                     ContatoDAO dao;
                                        try {
                                                dao = new ContatoDAO();
                                                dao.adiciona(cadastro);
                                                insereCod(codCli); 
                                                //contentPane.add(txtCodigo );
                                                codCli=leNextCodigo() ; 
                                                txtCodigo.setText(codCli.toString());
                                                //System.out.printf("linha 342 ..: %s \n ", codCli );; 
                                               // txtNome.requestFocus();
                                               txtNome.setEnabled(false ); 
                                               txtCep.setEnabled(false);
                                               txtLogradouro.setEnabled(false); 
                                               txtNumero.setEnabled(false); 
                                               txtComplemento.setEnabled(false); 
                                               txtBairro.setEnabled(false); 
                                               txtCidade.setEnabled(false); 
                                               txtUf.setEnabled(false); 
                                               
                                                
                                                
                                                 
                                        } catch (SQLException e1) {
                                                // TODO Auto-generated catch block
                                                e1.printStackTrace();
                                        }
                                   
                                        } 
                                else 
                                        txtNome.requestFocus();
                        }
                });
                txtUf.setCaretPosition(0);
                txtUf.setBounds(759, 147, 35, 20);
                contentPane.add(txtUf);
                
                

                JLabel lblDiv = new JLabel("-------------------------------------------------------------------------------------------------"); 
                lblDiv.setBounds(16, 243, 778, 20);
                contentPane.add(lblDiv);
          
                
                
                JLabel lblDoc = new JLabel( "Tp Doc.: ");
        		lblDoc.setBounds(16, 190, 108, 20);
                contentPane.add(lblDoc) ;
                
                //JTextField txtDoc = new JTextField() ; 
                
                
                
                JComboBox<TipoDoc> cmbDoc = new JComboBox<>();
                cmbDoc.setModel(new DefaultComboBoxModel<>(TipoDoc.values()));     
                cmbDoc.setBounds(76, 190, 105, 20);
                
                 
                cmbDoc.addItemListener((ItemListener) new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        if(e.getStateChange() == ItemEvent.SELECTED) // para evitar duplicações
                            System.out.println("Você escolheu a opção " + e.getItem());
                            opt=e.getItem().toString();
                            if  ( opt== "Cpf")
                            { 
                            	lblDoc.setText("Cpf ..: "); 
                            	JFormattedTextField txtCpf;
								try {
									txtCpf = new JFormattedTextField(new MaskFormatter("###.###.###-##") );
									 cmbDoc.addItemListener((ItemListener) new ItemListener() {
						                    public void itemStateChanged(ItemEvent e) {
						                           vrCpf = txtCpf.getText();
						                           
						                           boolean testeCpf=validaCPF.isCPF(vrCpf ); 
						                           if ( ! testeCpf  ){ 
						                        	   System.out.println("CPF Invalido ");
						                           }
						                           
						                    }   
						               });
									 
									txtCpf.setBounds(76, 190, 120, 20);
	                            	contentPane.add(txtCpf);
								} catch (ParseException e1) {
									
									e1.printStackTrace();
								}
                             
                            }
                            else 
                            { 
                            	lblDoc.setText("Cnpj..: "); 
                            	
							    JFormattedTextField txtCnpj;
								try {
								
									txtCnpj = new JFormattedTextField(new MaskFormatter("##.###.###/####-##") );
									txtCnpj.setBounds(76, 190, 160, 20);
								    contentPane.add(txtCnpj);
								} catch (ParseException e1) {
									
									e1.printStackTrace();
								}
							     
                            } 
                            	
                            
                            cmbDoc.setVisible(false); 
                            
                                        
                            
                            
                            
                    }
                });
                
                
                
                
                
                
                contentPane.add(cmbDoc) ; 
                
                
                Button btnSair = new Button("Sair");
                btnSair.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                DB.closeConnection() ;

                                System.exit(0);
                        }
                });

                btnSair.setForeground(Color.RED); 
                btnSair.setBounds(660, 500, 117, 29);
                contentPane.add(btnSair);
                /*
                txtCpf = new JTextField("684.789.847-04");
                txtCpf.setCaretPosition(0);
                txtCpf.setBounds(112, 307, 117, 20);
                contentPane.add(txtCpf);
                */
                }
    
           
        
        

            public static  Endereco  veCep ( String  cep) throws Exception    {
                    //System.out.printf("CEP ....::: %s\n\n\n ",cep);
                    String cepAux = cep.replaceAll("-", "");
                    cep=cepAux ;
                    //System.out.printf("CEPAUX .::: [%s] \n\n\n ",cep);
                    
                    Endereco endereco=null; 
                    if ( cep.equals("") ) 
                        JOptionPane.showMessageDialog(null, "Favor Digitar o Cep "); 
                    else         
                        endereco = ServicoDeCep.buscaEnderecoPelo(cep);
                   //   System.out.println("Endereço Retornado  :"+ endereco );
                       
                return(  endereco ) ;
                
                
                
                
                
                
                
        }
          
            
            
            public static Integer  leNextCodigo() throws SQLException { 
            
                ContatoDAO concod = new ContatoDAO() ; 
                        Integer codCli  =   concod.selCod() ; 
                        //String codCliaux  =""+ codCli; 
                        return codCli ; 
            
            
            }  
            
            
            public void insereCod(Integer codCli) throws SQLException { 
                ContatoDAO concod = new ContatoDAO() ; 
                        //System.out.println("insereCod"+codCli );   
                    concod.insCod(codCli)  ; 

            }
}