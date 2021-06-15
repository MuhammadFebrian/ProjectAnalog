import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Transaksi {

	private JFrame frmBbm;
	private JTextField txtLiter;
	private JTable table;
	private JTextField txtHarga;
	private JTextField txtJenis;
	private JTextField txtHargaLiter;
	private JTextField txtJumlah;
	private JTextField txtTotal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Transaksi window = new Transaksi();
					window.frmBbm.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	// Fungsi untuk menampilkan data dari database ke tabel
	public void tableShow() {
		
		try {
			String query = "select * from pengisian";
			Connection con = (Connection)JDBC.jdbc();
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery(query);
			
			DefaultTableModel dtm = (DefaultTableModel)table.getModel();
	        dtm.setRowCount(0);
	        
	        String [] data = new String [5];
	        
	        while(rs.next()) {
	            data[0] = rs.getString("tgl");
	            data[1] = rs.getString("jam");
	            data[2] = rs.getString("jenis");
	            data[3] = rs.getString("liter");
	            data[4] = rs.getString("total");
	            
	            dtm.addRow(data); 
	        }
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Gagal Insert Data");
		}
	}
	
	// Fungsi untuk format tanggal sekarang
	private String getTanggal() {  
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
        Date date = new Date();  
        return dateFormat.format(date);  
    }  
     
	// Fungsi untuk format waktu sekarang
    private String getWaktu() {  
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");  
        Date date = new Date();  
        return dateFormat.format(date);  
    } 

	/**
	 * Create the application.
	 */
	public Transaksi() {
		initialize();
		tableShow();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBbm = new JFrame();
		frmBbm.getContentPane().setBackground(new Color(178, 34, 34));
		frmBbm.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Pengisian Bahan Bakar");
		lblNewLabel.setBounds(228, 22, 222, 40);
		frmBbm.getContentPane().add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(128, 0, 0));
		lblNewLabel.setFont(new Font("Stencil", Font.PLAIN, 16));
		
		JPanel panel = new JPanel();
		panel.setBounds(228, 22, 222, 40);
		frmBbm.getContentPane().add(panel);
		panel.setLayout(null);
		
		ButtonGroup jenis = new ButtonGroup();
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(255, 255, 255)), "Pengisian", TitledBorder.TRAILING, TitledBorder.TOP, null, new Color(255, 255, 255)));
		panel_1.setOpaque(false);
		panel_1.setBounds(10, 91, 263, 359);
		frmBbm.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_1 = new JLabel("Jumlah Liter BBM");
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.BOLD, 19));
		lblNewLabel_1_1.setBounds(41, 219, 185, 17);
		panel_1.add(lblNewLabel_1_1);
		
		txtLiter = new JTextField();
		txtLiter.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char karakter = e.getKeyChar();
				if (!(((karakter >= '0') && (karakter <= '9') || (karakter == KeyEvent.VK_BACK_SPACE) || (karakter == KeyEvent.VK_DELETE)))) {
					e.consume();
				}
			}
		});
		txtLiter.setFont(new Font("Times New Roman", Font.BOLD, 14));
		txtLiter.setBounds(51, 247, 105, 29);
		panel_1.add(txtLiter);
		txtLiter.setColumns(10);
		
		JLabel lblNewLabel_1_2 = new JLabel("liter");
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_2.setForeground(Color.WHITE);
		lblNewLabel_1_2.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 16));
		lblNewLabel_1_2.setBounds(156, 253, 59, 17);
		panel_1.add(lblNewLabel_1_2);
		
		JButton btnNewButton = new JButton("Lakukan Pengisian");
		
		// Fungsi tombol pengisian
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(btnNewButton, "Lanjut pengisian?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
				
				long total = Long.valueOf(txtHarga.getText()) * Long.valueOf(txtLiter.getText());
				
				try {
					if (Long.parseLong(txtHarga.getText()) <= 0) {
						JOptionPane.showMessageDialog(btnNewButton, "Pilih jenis BBM");
					} else if (Integer.parseInt(txtLiter.getText()) <= 0) {
						JOptionPane.showMessageDialog(btnNewButton, "Masukkan jumlah liter");
					} else if (confirm==0) {
						JOptionPane.showMessageDialog(btnNewButton, "Pengisian berhasil");
					
						txtJenis.setText(jenis.getSelection().getActionCommand());
						txtHargaLiter.setText(txtHarga.getText());
						txtJumlah.setText(txtLiter.getText());
						txtTotal.setText(String.valueOf(total));
						txtHarga.setText("");
						txtLiter.setText("");
						jenis.clearSelection();
					} else {
						JOptionPane.showMessageDialog(btnNewButton, "Gagal Mengisi");
					}
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(btnNewButton, "Yang lengkap ya...."+e2);
				}
				
				
			}
		});
		btnNewButton.setFocusable(false);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNewButton.setBackground(new Color(25,25,112));
				btnNewButton.setForeground(new Color(240,248,255));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton.setBackground(new Color(255, 255, 255));
				btnNewButton.setForeground(Color.BLACK);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNewButton.setBackground(new Color(25,25,112));
				btnNewButton.setForeground(new Color(240,248,255));
			}
		});
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.setFont(new Font("Cambria", Font.BOLD, 15));
		btnNewButton.setBounds(22, 287, 218, 46);
		panel_1.add(btnNewButton);
		
		JRadioButton pertamax = new JRadioButton("   Pertamax");
		pertamax.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtHarga.setText("10500");
			}
		});
		pertamax.setBounds(57, 134, 139, 23);
		panel_1.add(pertamax);
		pertamax.setFocusable(false);
		pertamax.setForeground(new Color(0, 255, 255));
		pertamax.setFont(new Font("Tahoma", Font.BOLD, 16));
		pertamax.setBackground(new Color(178, 34, 34));
		jenis.add(pertamax);
		pertamax.setActionCommand("Pertamax");
		
		JRadioButton pertalite = new JRadioButton("   Pertalite");
		pertalite.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtHarga.setText("8000");
			}
		});
		pertalite.setBounds(57, 97, 121, 23);
		panel_1.add(pertalite);
		pertalite.setFocusable(false);
		pertalite.setForeground(new Color(0, 255, 127));
		pertalite.setFont(new Font("Tahoma", Font.BOLD, 16));
		pertalite.setBackground(new Color(178, 34, 34));
		jenis.add(pertalite);
		pertalite.setActionCommand("Pertalite");
		
		JRadioButton premium = new JRadioButton("   Premium");
		premium.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtHarga.setText("6500");
			}
		});
		premium.setBounds(57, 63, 121, 23);
		panel_1.add(premium);
		premium.setFocusable(false);
		premium.setFont(new Font("Tahoma", Font.BOLD, 16));
		premium.setBackground(new Color(178, 34, 34));
		premium.setForeground(new Color(255, 255, 0));
		jenis.add(premium);
		premium.setActionCommand("Premium");
		
		JLabel lblNewLabel_1 = new JLabel("Pilih Jenis BBM");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(41, 30, 155, 17);
		panel_1.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 19));
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		
		txtHarga = new JTextField();
		txtHarga.setEditable(false);
		txtHarga.setFont(new Font("Times New Roman", Font.BOLD, 14));
		txtHarga.setColumns(10);
		txtHarga.setBounds(57, 164, 89, 29);
		panel_1.add(txtHarga);
		
		JLabel lblNewLabel_1_2_2 = new JLabel("per liter");
		lblNewLabel_1_2_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_2_2.setForeground(Color.WHITE);
		lblNewLabel_1_2_2.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 16));
		lblNewLabel_1_2_2.setBounds(156, 170, 59, 17);
		panel_1.add(lblNewLabel_1_2_2);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(255, 255, 255)));
		panel_2.setBackground(new Color(178, 34, 34));
		panel_2.setBounds(283, 257, 391, 193);
		frmBbm.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 33, 371, 149);
		panel_2.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Tanggal", "Jam", "Jenis BBM", "Jumlah Liter", "Jumlah Bayar"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, Integer.class, Long.class
			};
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, true, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("record pengisian bbm");
		lblNewLabel_1_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_2_1.setForeground(Color.WHITE);
		lblNewLabel_1_2_1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_1_2_1.setBounds(122, 5, 155, 17);
		panel_2.add(lblNewLabel_1_2_1);
		
		JButton btnNewButton_2 = new JButton("Hapus");
		
		// Fungsi untuk menghapus seluruh data record
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(btnNewButton_2, "Apakah anda yakin ingin menghapus seluruh record?", "Konfirmasi dulu ya...", JOptionPane.YES_NO_OPTION);
				
				try {
					String query = "truncate table pengisian";
					Connection conn = (Connection)JDBC.jdbc();
					PreparedStatement st = conn.prepareStatement(query);
					
					if (confirm==0) {
						st.executeUpdate();
						JOptionPane.showMessageDialog(btnNewButton_2, "Berhasil menghapus data...");
						tableShow();
					}
					
					 if (conn != null) {
				        	try {
				        		conn.close();
							} catch (SQLException e4) {}
				     }
					 
					 if (st != null) {
				        	try {
				        		st.close();
							} catch (SQLException e5) {}
				     }
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(btnNewButton_2, "Gagal!!!");
				}
			}
		});
		btnNewButton_2.setFocusable(false);
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNewButton_2.setBackground(new Color(25,25,112));
				btnNewButton_2.setForeground(new Color(240,248,255));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton_2.setBackground(new Color(255, 255, 255));
				btnNewButton_2.setForeground(Color.BLACK);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNewButton_2.setBackground(new Color(25,25,112));
				btnNewButton_2.setForeground(new Color(240,248,255));
			}
		});
		btnNewButton_2.setFont(new Font("Cambria", Font.PLAIN, 11));
		btnNewButton_2.setBackground(Color.WHITE);
		btnNewButton_2.setBounds(10, 5, 75, 23);
		panel_2.add(btnNewButton_2);
		
		JButton btnNewButton_1 = new JButton("Bayar");
		
		// Fungsi tombol bayar dan menyimpan data record ke database
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String sql = "insert into pengisian values('"+getTanggal()+"','"+getWaktu()+"','"+txtJenis.getText()+"','"+txtHargaLiter.getText()+"','"+txtTotal.getText()+"')";
					Connection con = (Connection)JDBC.jdbc();
					PreparedStatement pst = con.prepareStatement(sql);
					pst.execute();
					JOptionPane.showMessageDialog(txtTotal, "Proses pembayaran berhasil");
					tableShow();
					
					txtJenis.setText("");
					txtHargaLiter.setText("");
					txtJumlah.setText("");
					txtTotal.setText("");
					
					 if (con != null) {
				        	try {
				        		con.close();
							} catch (SQLException e4) {}
				     }
					 
					 if (pst != null) {
				        	try {
				        		pst.close();
							} catch (SQLException e5) {}
				     }
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(txtTotal, "Periksa lagi inputan anda.", "KESALAHAN INPUT!!", 0);
				}
				
				
			}
		});
		btnNewButton_1.setFocusable(false);
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNewButton_1.setBackground(new Color(25,25,112));
				btnNewButton_1.setForeground(new Color(240,248,255));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton_1.setBackground(new Color(255, 255, 255));
				btnNewButton_1.setForeground(Color.BLACK);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnNewButton_1.setBackground(new Color(25,25,112));
				btnNewButton_1.setForeground(new Color(240,248,255));
			}
		});
		btnNewButton_1.setBackground(new Color(255, 255, 255));
		btnNewButton_1.setFont(new Font("Cambria", Font.BOLD, 12));
		btnNewButton_1.setBounds(578, 213, 96, 33);
		frmBbm.getContentPane().add(btnNewButton_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Transaksi.class.getResource("/gambar/pom1.jpg")));
		lblNewLabel_2.setBounds(576, 98, 98, 104);
		frmBbm.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Jenis BBM");
		lblNewLabel_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1_1_1.setBounds(283, 108, 96, 17);
		frmBbm.getContentPane().add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Harga per liter");
		lblNewLabel_1_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_1_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1_1_1_1.setBounds(283, 144, 130, 17);
		frmBbm.getContentPane().add(lblNewLabel_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1_2 = new JLabel("Jumlah diisi");
		lblNewLabel_1_1_1_2.setForeground(Color.WHITE);
		lblNewLabel_1_1_1_2.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1_1_1_2.setBounds(283, 185, 96, 17);
		frmBbm.getContentPane().add(lblNewLabel_1_1_1_2);
		
		JLabel lblNewLabel_1_1_1_3 = new JLabel("Total bayar");
		lblNewLabel_1_1_1_3.setForeground(Color.WHITE);
		lblNewLabel_1_1_1_3.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1_1_1_3.setBounds(283, 221, 130, 17);
		frmBbm.getContentPane().add(lblNewLabel_1_1_1_3);
		
		txtJenis = new JTextField();
		txtJenis.setEditable(false);
		txtJenis.setBounds(423, 106, 130, 23);
		frmBbm.getContentPane().add(txtJenis);
		txtJenis.setColumns(10);
		
		txtHargaLiter = new JTextField();
		txtHargaLiter.setEditable(false);
		txtHargaLiter.setColumns(10);
		txtHargaLiter.setBounds(423, 143, 130, 23);
		frmBbm.getContentPane().add(txtHargaLiter);
		
		txtJumlah = new JTextField();
		txtJumlah.setEditable(false);
		txtJumlah.setColumns(10);
		txtJumlah.setBounds(423, 183, 130, 23);
		frmBbm.getContentPane().add(txtJumlah);
		
		txtTotal = new JTextField();
		txtTotal.setEditable(false);
		txtTotal.setColumns(10);
		txtTotal.setBounds(423, 220, 130, 23);
		frmBbm.getContentPane().add(txtTotal);
		frmBbm.setIconImage(Toolkit.getDefaultToolkit().getImage(Transaksi.class.getResource("/gambar/spbu.png")));
		frmBbm.setTitle("BBM");
		frmBbm.setBounds(340, 100, 700, 500);
		frmBbm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
