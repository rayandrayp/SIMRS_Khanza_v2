/*
  Dilarang keras menggandakan/mengcopy/menyebarkan/membajak/mendecompile 
  Software ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */

package inventory;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import simrskhanza.DlgCariBangsal;

/**
 *
 * @author dosen
 */
public final class DlgStokOpname2 extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();  
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement pstampil;
    private ResultSet rstampil;
    private DlgCariJenis jenis = new DlgCariJenis(null, false);
    private DlgCariKategori kategori = new DlgCariKategori(null, false);
    private DlgCariGolongan golongan = new DlgCariGolongan(null, false);
    private DlgCariBangsal bangsal=new DlgCariBangsal(null,false);
    private DecimalFormat df2 = new DecimalFormat("###,###,###,###,###,###,###");
    double stok_awal=0,masuk=0,keluar=0,sisa=0,jumlah=0;
    private String sql_cond_jenis="",sql_cond_kategori="",sql_cond_golongan="";
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public DlgStokOpname2(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(628,674);

        Object[] row={"NO.","NAMA MATKES","SATUAN","ED","HARGA","STOK AWAL","MASUK","KELUAR","SISA","JUMLAH"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbKamar.setModel(tabMode);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbKamar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 10; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(30);
            }else if(i==1){
                column.setPreferredWidth(200);
            }else if(i==2){
                column.setPreferredWidth(90);
            }else if(i==3){
                column.setPreferredWidth(90);
            }else if(i==4){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setPreferredWidth(60);
            }else if(i==6){
                column.setPreferredWidth(60);
            }else if(i==7){
                column.setPreferredWidth(60);
            }else if(i==8){
                column.setPreferredWidth(60);
            }else if(i==9){
                column.setPreferredWidth(100);
            }
        }
        tbKamar.setDefaultRenderer(Object.class, new WarnaTable());
        
        Kdbar.setDocument(new batasInput((byte)15).getKata(Kdbar));
        Stok.setDocument(new batasInput((byte)10).getKata(Stok));
        Real.setDocument(new batasInput((byte)10).getOnlyAngka(Real));
        Keterangan.setDocument(new batasInput((byte)60).getKata(Keterangan));
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil("riwayat");
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil("riwayat");
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil("riwayat");
                    }
                }
            });
        }
        
        jenis.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (jenis.getTable().getSelectedRow() != -1) {
                    kdjenis.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(), 0).toString());
                    nmjns.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(), 1).toString());
                }
                TCari.requestFocus();
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        
        golongan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (golongan.getTable().getSelectedRow() != -1) {
                    kdgolongan.setText(golongan.getTable().getValueAt(golongan.getTable().getSelectedRow(), 0).toString());
                    nmgolongan.setText(golongan.getTable().getValueAt(golongan.getTable().getSelectedRow(), 1).toString());
                }
                
                TCari.requestFocus();
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
                golongan.emptTeks();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        
        kategori.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (kategori.getTable().getSelectedRow() != -1) {
                    kdkategori.setText(kategori.getTable().getValueAt(kategori.getTable().getSelectedRow(), 0).toString());
                    nmkategori.setText(kategori.getTable().getValueAt(kategori.getTable().getSelectedRow(), 1).toString());
                }
                TCari.requestFocus();
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
                kategori.emptTeks();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        
        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(bangsal.getTable().getSelectedRow()!= -1){                   
                    KdGudang.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),0).toString());
                    NmGudang.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
                }  
                KdGudang.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
    } 
    double total=0,totalreal=0,totallebih=0;


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Kd2 = new widget.TextBox();
        panelisi4 = new widget.panelisi();
        label34 = new widget.Label();
        label32 = new widget.Label();
        Stok = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        label17 = new widget.Label();
        Kdbar = new widget.TextBox();
        Nmbar = new widget.TextBox();
        Harga = new widget.TextBox();
        label36 = new widget.Label();
        Real = new widget.TextBox();
        Selisih = new widget.TextBox();
        label37 = new widget.Label();
        label38 = new widget.Label();
        Nominal = new widget.TextBox();
        Keterangan = new widget.TextBox();
        label18 = new widget.Label();
        kdgudang = new widget.TextBox();
        nmgudang = new widget.TextBox();
        label39 = new widget.Label();
        Lebih = new widget.TextBox();
        NomiLebih = new widget.TextBox();
        TotalReal = new widget.TextBox();
        kdjenis = new widget.TextBox();
        kdkategori = new widget.TextBox();
        kdgolongan = new widget.TextBox();
        nobatch = new widget.TextBox();
        nofaktur = new widget.TextBox();
        KdGudang = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbKamar = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelisi3 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label19 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label24 = new widget.Label();
        NmGudang = new widget.TextBox();
        BtnKategori1 = new widget.Button();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        panelisi1 = new widget.panelisi();
        BtnHapus = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        label12 = new widget.Label();
        LTotal = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelBiasa1 = new widget.PanelBiasa();
        label20 = new widget.Label();
        nmjns = new widget.TextBox();
        BtnJenis = new widget.Button();
        label22 = new widget.Label();
        nmkategori = new widget.TextBox();
        BtnKategori = new widget.Button();
        label23 = new widget.Label();
        nmgolongan = new widget.TextBox();
        BtnGolongan = new widget.Button();

        Kd2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kd2.setHighlighter(null);
        Kd2.setName("Kd2"); // NOI18N
        Kd2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kd2KeyPressed(evt);
            }
        });

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 103));
        panelisi4.setLayout(null);

        label34.setText("Stok :");
        label34.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label34.setName("label34"); // NOI18N
        label34.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label34);
        label34.setBounds(0, 40, 55, 23);

        label32.setText("Tanggal :");
        label32.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label32);
        label32.setBounds(550, 10, 60, 23);

        Stok.setEditable(false);
        Stok.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Stok.setHighlighter(null);
        Stok.setName("Stok"); // NOI18N
        Stok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StokKeyPressed(evt);
            }
        });
        panelisi4.add(Stok);
        Stok.setBounds(59, 40, 70, 23);

        Tanggal.setEditable(false);
        Tanggal.setDisplayFormat("yyyy-MM-dd");
        Tanggal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Tanggal.setName("Tanggal"); // NOI18N
        panelisi4.add(Tanggal);
        Tanggal.setBounds(613, 10, 95, 23);

        label17.setText("Barang :");
        label17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label17);
        label17.setBounds(0, 10, 55, 23);

        Kdbar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kdbar.setName("Kdbar"); // NOI18N
        Kdbar.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi4.add(Kdbar);
        Kdbar.setBounds(59, 10, 90, 23);

        Nmbar.setEditable(false);
        Nmbar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Nmbar.setName("Nmbar"); // NOI18N
        Nmbar.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(Nmbar);
        Nmbar.setBounds(151, 10, 257, 23);

        Harga.setEditable(false);
        Harga.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Harga.setHighlighter(null);
        Harga.setName("Harga"); // NOI18N
        panelisi4.add(Harga);
        Harga.setBounds(410, 10, 110, 23);

        label36.setText("Real :");
        label36.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label36.setName("label36"); // NOI18N
        label36.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label36);
        label36.setBounds(130, 40, 40, 23);

        Real.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Real.setHighlighter(null);
        Real.setName("Real"); // NOI18N
        Real.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RealKeyPressed(evt);
            }
        });
        panelisi4.add(Real);
        Real.setBounds(174, 40, 55, 23);

        Selisih.setEditable(false);
        Selisih.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Selisih.setHighlighter(null);
        Selisih.setName("Selisih"); // NOI18N
        panelisi4.add(Selisih);
        Selisih.setBounds(284, 40, 55, 23);

        label37.setText("Selisih :");
        label37.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label37.setName("label37"); // NOI18N
        label37.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label37);
        label37.setBounds(230, 40, 50, 23);

        label38.setText("Nominal Hilang :");
        label38.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label38.setName("label38"); // NOI18N
        label38.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label38);
        label38.setBounds(474, 40, 100, 23);

        Nominal.setEditable(false);
        Nominal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Nominal.setHighlighter(null);
        Nominal.setName("Nominal"); // NOI18N
        Nominal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NominalKeyPressed(evt);
            }
        });
        panelisi4.add(Nominal);
        Nominal.setBounds(578, 40, 130, 23);

        Keterangan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Keterangan.setHighlighter(null);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        panelisi4.add(Keterangan);
        Keterangan.setBounds(578, 70, 130, 23);

        label18.setText("Lokasi :");
        label18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label18);
        label18.setBounds(0, 70, 55, 23);

        kdgudang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        kdgudang.setName("kdgudang"); // NOI18N
        kdgudang.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi4.add(kdgudang);
        kdgudang.setBounds(59, 70, 90, 23);

        nmgudang.setEditable(false);
        nmgudang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        nmgudang.setName("nmgudang"); // NOI18N
        nmgudang.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(nmgudang);
        nmgudang.setBounds(151, 70, 257, 23);

        label39.setText("Keterangan :");
        label39.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label39.setName("label39"); // NOI18N
        label39.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label39);
        label39.setBounds(474, 70, 100, 23);

        Lebih.setEditable(false);
        Lebih.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Lebih.setHighlighter(null);
        Lebih.setName("Lebih"); // NOI18N
        panelisi4.add(Lebih);
        Lebih.setBounds(284, 40, 55, 23);

        NomiLebih.setEditable(false);
        NomiLebih.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        NomiLebih.setHighlighter(null);
        NomiLebih.setName("NomiLebih"); // NOI18N
        NomiLebih.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NomiLebihKeyPressed(evt);
            }
        });
        panelisi4.add(NomiLebih);
        NomiLebih.setBounds(578, 40, 130, 23);

        TotalReal.setEditable(false);
        TotalReal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TotalReal.setHighlighter(null);
        TotalReal.setName("TotalReal"); // NOI18N
        TotalReal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalRealKeyPressed(evt);
            }
        });
        panelisi4.add(TotalReal);
        TotalReal.setBounds(578, 40, 130, 23);

        kdjenis.setEditable(false);
        kdjenis.setName("kdjenis"); // NOI18N
        kdjenis.setPreferredSize(new java.awt.Dimension(75, 23));
        kdjenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdjenisKeyPressed(evt);
            }
        });

        kdkategori.setEditable(false);
        kdkategori.setName("kdkategori"); // NOI18N
        kdkategori.setPreferredSize(new java.awt.Dimension(75, 23));
        kdkategori.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdkategoriKeyPressed(evt);
            }
        });

        kdgolongan.setEditable(false);
        kdgolongan.setName("kdgolongan"); // NOI18N
        kdgolongan.setPreferredSize(new java.awt.Dimension(75, 23));
        kdgolongan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdgolonganKeyPressed(evt);
            }
        });

        nobatch.setEditable(false);
        nobatch.setName("nobatch"); // NOI18N
        nobatch.setPreferredSize(new java.awt.Dimension(75, 23));
        nobatch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nobatchKeyPressed(evt);
            }
        });

        nofaktur.setEditable(false);
        nofaktur.setName("nofaktur"); // NOI18N
        nofaktur.setPreferredSize(new java.awt.Dimension(75, 23));
        nofaktur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nofakturKeyPressed(evt);
            }
        });

        KdGudang.setEditable(false);
        KdGudang.setName("KdGudang"); // NOI18N
        KdGudang.setPreferredSize(new java.awt.Dimension(150, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Stok Opname Obat, Alkes & BHP Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamar.setAutoCreateRowSorter(true);
        tbKamar.setName("tbKamar"); // NOI18N
        tbKamar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKamarMouseClicked(evt);
            }
        });
        tbKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKamarKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbKamar);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi3.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi3.add(Tgl1);

        label19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label19.setText("s.d.");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(30, 23));
        panelisi3.add(label19);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi3.add(Tgl2);

        label24.setText("Lokasi :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi3.add(label24);

        NmGudang.setEditable(false);
        NmGudang.setName("NmGudang"); // NOI18N
        NmGudang.setPreferredSize(new java.awt.Dimension(150, 23));
        panelisi3.add(NmGudang);

        BtnKategori1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKategori1.setMnemonic('2');
        BtnKategori1.setToolTipText("Alt+2");
        BtnKategori1.setName("BtnKategori1"); // NOI18N
        BtnKategori1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnKategori1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKategori1ActionPerformed(evt);
            }
        });
        panelisi3.add(BtnKategori1);

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(190, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi3.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
        BtnCari.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelisi3.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('3');
        BtnAll.setToolTipText("Alt+3");
        BtnAll.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelisi3.add(BtnAll);

        jPanel1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelisi1.add(BtnHapus);

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(47, 30));
        panelisi1.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(40, 30));
        panelisi1.add(LCount);

        label12.setText("Total : ");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(50, 30));
        panelisi1.add(label12);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(85, 30));
        panelisi1.add(LTotal);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelisi1.add(BtnPrint);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelisi1.add(BtnKeluar);

        jPanel1.add(panelisi1, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelBiasa1.setName("panelBiasa1"); // NOI18N
        panelBiasa1.setPreferredSize(new java.awt.Dimension(1023, 47));
        panelBiasa1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 10));

        label20.setText("Jenis :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(40, 23));
        panelBiasa1.add(label20);

        nmjns.setEditable(false);
        nmjns.setName("nmjns"); // NOI18N
        nmjns.setPreferredSize(new java.awt.Dimension(192, 23));
        panelBiasa1.add(nmjns);

        BtnJenis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnJenis.setMnemonic('2');
        BtnJenis.setToolTipText("Alt+2");
        BtnJenis.setName("BtnJenis"); // NOI18N
        BtnJenis.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJenisActionPerformed(evt);
            }
        });
        panelBiasa1.add(BtnJenis);

        label22.setText("Kategori :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(65, 23));
        panelBiasa1.add(label22);

        nmkategori.setEditable(false);
        nmkategori.setName("nmkategori"); // NOI18N
        nmkategori.setPreferredSize(new java.awt.Dimension(192, 23));
        panelBiasa1.add(nmkategori);

        BtnKategori.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKategori.setMnemonic('2');
        BtnKategori.setToolTipText("Alt+2");
        BtnKategori.setName("BtnKategori"); // NOI18N
        BtnKategori.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKategoriActionPerformed(evt);
            }
        });
        panelBiasa1.add(BtnKategori);

        label23.setText("Golongan :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(65, 23));
        panelBiasa1.add(label23);

        nmgolongan.setEditable(false);
        nmgolongan.setName("nmgolongan"); // NOI18N
        nmgolongan.setPreferredSize(new java.awt.Dimension(192, 23));
        panelBiasa1.add(nmgolongan);

        BtnGolongan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnGolongan.setMnemonic('2');
        BtnGolongan.setToolTipText("Alt+2");
        BtnGolongan.setName("BtnGolongan"); // NOI18N
        BtnGolongan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnGolongan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGolonganActionPerformed(evt);
            }
        });
        panelBiasa1.add(BtnGolongan);

        internalFrame1.add(panelBiasa1, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        Valid.hapusTable(tabMode,Kdbar,"opname","kd_bangsal='"+kdgudang.getText()+"' and tanggal='"+Tanggal.getSelectedItem()+"' and no_batch='"+nobatch.getText()+"' and no_faktur='"+nofaktur.getText()+"' and kode_brng");
        BtnCariActionPerformed(evt);
        emptTeks();
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnAll,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
        if(tbKamar.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tbKamar.getRowCount()!=0){   
            try {
                exportSEPExcelActionPerformed(evt);
            } catch (IOException ex) {
                Logger.getLogger(DlgStokOpname2.class.getName()).log(Level.SEVERE, null, ex);
            }
//            Map<String, Object> param = new HashMap<>();    
//            param.put("namars",akses.getnamars());
//            param.put("alamatrs",akses.getalamatrs());
//            param.put("kotars",akses.getkabupatenrs());
//            param.put("propinsirs",akses.getpropinsirs());
//            param.put("kontakrs",akses.getkontakrs());
//            param.put("emailrs",akses.getemailrs());   
//            param.put("logo",Sequel.cariGambar("select logo from setting")); 
//            if(nmjns.getText().equals("")&&nmkategori.getText().equals("")&&nmgolongan.getText().equals("")&&NmGudang.getText().equals("")&&TCari.getText().equals("")){
//                Valid.MyReportqry("rptOpname.jasper","report","::[ Stok Opname ]::",
//                    "select opname.kode_brng, databarang.nama_brng,opname.h_beli, databarang.kode_sat, opname.tanggal, opname.stok, "+
//                    "opname.real, opname.selisih, opname.lebih, (opname.real*opname.h_beli) as totalreal,opname.nomihilang,opname.nomilebih, opname.keterangan, bangsal.kd_bangsal, bangsal.nm_bangsal, "+
//                    "opname.no_batch,opname.no_faktur from opname inner join databarang on opname.kode_brng=databarang.kode_brng "+
//                    "inner join bangsal on opname.kd_bangsal=bangsal.kd_bangsal "+
//                    "inner join jenis on databarang.kdjns=jenis.kdjns "+
//                    "inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode "+
//                    "inner join golongan_barang on databarang.kode_golongan=golongan_barang.kode where "+
//                     "opname.tanggal between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' order by opname.tanggal",param);
//            }else{
//                Valid.MyReportqry("rptOpname.jasper","report","::[ Stok Opname ]::",
//                     "select opname.kode_brng, databarang.nama_brng,opname.h_beli, databarang.kode_sat, opname.tanggal, opname.stok, "+
//                     "opname.real, opname.selisih, opname.lebih, (opname.real*opname.h_beli) as totalreal,opname.nomihilang,opname.nomilebih, opname.keterangan, bangsal.kd_bangsal, bangsal.nm_bangsal, "+
//                     "opname.no_batch,opname.no_faktur from opname inner join databarang on opname.kode_brng=databarang.kode_brng "+
//                     "inner join bangsal on opname.kd_bangsal=bangsal.kd_bangsal "+
//                     "inner join jenis on databarang.kdjns=jenis.kdjns "+
//                     "inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode "+
//                     "inner join golongan_barang on databarang.kode_golongan=golongan_barang.kode where "+
//                     "opname.tanggal between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' "+
//                     "and concat(databarang.kdjns,jenis.nama) like '%"+kdjenis.getText()+nmjns.getText().trim()+"%' "+
//                     "and concat(databarang.kode_kategori,kategori_barang.nama) like '%"+kdkategori.getText()+nmkategori.getText().trim()+"%' "+
//                     "and concat(databarang.kode_golongan,golongan_barang.nama) like '%"+kdgolongan.getText()+nmgolongan.getText().trim()+"%' "+
//                     "and concat(bangsal.kd_bangsal,bangsal.nm_bangsal) like '%"+KdGudang.getText()+NmGudang.getText().trim()+"%' "+
//                     "and (opname.kode_brng like '%"+TCari.getText().trim()+"%' or databarang.nama_brng like '%"+TCari.getText().trim()+"%' or "+
//                     "opname.kode_brng like '%"+TCari.getText().trim()+"%' or bangsal.kd_bangsal like '%"+TCari.getText().trim()+"%' or "+
//                     "bangsal.nm_bangsal like '%"+TCari.getText().trim()+"%' or databarang.kode_sat like '%"+TCari.getText().trim()+"%' or "+
//                     "opname.keterangan like '%"+TCari.getText().trim()+"%') order by opname.tanggal",param);
//            }
                
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbKamar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        String opsi = (String)JOptionPane.showInputDialog(null,"Silahkan pilih jenis sumber stok!","Jenis",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Riwayat Transaksi Obat", "Stok Opname"},"Jenis");
        switch (opsi) {
              case "Riwayat Transaksi Obat":
                    tampil("riwayat");
                    break;
              case "Stok Opname":
                    tampil("stokopname");
                    break;
        }
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void tbKamarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKamarMouseClicked
        
}//GEN-LAST:event_tbKamarMouseClicked

    private void tbKamarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKamarKeyPressed
        
}//GEN-LAST:event_tbKamarKeyPressed

    private void Kd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kd2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kd2KeyPressed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        nmgolongan.setText("");
        nmjns.setText("");
        nmkategori.setText("");
        kdgolongan.setText("");
        kdjenis.setText("");
        kdkategori.setText("");
        KdGudang.setText("");
        NmGudang.setText("");
        tampil("riwayat");
    }//GEN-LAST:event_BtnAllActionPerformed

private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt,Real,Tanggal);
}//GEN-LAST:event_KeteranganKeyPressed

private void NominalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NominalKeyPressed
        // TODO add your handling code here:
}//GEN-LAST:event_NominalKeyPressed

private void RealKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RealKeyPressed
        Valid.pindah(evt,Kdbar,Keterangan);
}//GEN-LAST:event_RealKeyPressed

private void StokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StokKeyPressed
        Valid.pindah(evt,TCari,Tanggal);
}//GEN-LAST:event_StokKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
       //tampil();
    }//GEN-LAST:event_formWindowOpened

    private void BtnJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJenisActionPerformed
        jenis.isCek();
        jenis.setSize(internalFrame1.getWidth() -20, internalFrame1.getHeight() -20);
        jenis.setLocationRelativeTo(internalFrame1);
        jenis.setAlwaysOnTop(false);
        jenis.setVisible(true);
    }//GEN-LAST:event_BtnJenisActionPerformed

    private void BtnKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKategoriActionPerformed
        kategori.isCek();
        kategori.setSize(internalFrame1.getWidth() -20, internalFrame1.getHeight() -20);
        kategori.setLocationRelativeTo(internalFrame1);
        kategori.setAlwaysOnTop(false);
        kategori.setVisible(true);
    }//GEN-LAST:event_BtnKategoriActionPerformed

    private void BtnGolonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGolonganActionPerformed
        golongan.isCek();
        golongan.setSize(internalFrame1.getWidth() -20, internalFrame1.getHeight() -20);
        golongan.setLocationRelativeTo(internalFrame1);
        golongan.setAlwaysOnTop(false);
        golongan.setVisible(true);
    }//GEN-LAST:event_BtnGolonganActionPerformed

    private void kdjenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdjenisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kdjenisKeyPressed

    private void kdkategoriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdkategoriKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kdkategoriKeyPressed

    private void kdgolonganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdgolonganKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kdgolonganKeyPressed

    private void nobatchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nobatchKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nobatchKeyPressed

    private void nofakturKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nofakturKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nofakturKeyPressed

    private void NomiLebihKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NomiLebihKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NomiLebihKeyPressed

    private void TotalRealKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TotalRealKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TotalRealKeyPressed

    private void BtnKategori1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKategori1ActionPerformed
        bangsal.isCek();
        bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setAlwaysOnTop(false);
        bangsal.setVisible(true);   
    }//GEN-LAST:event_BtnKategori1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgStokOpname2 dialog = new DlgStokOpname2(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnGolongan;
    private widget.Button BtnHapus;
    private widget.Button BtnJenis;
    private widget.Button BtnKategori;
    private widget.Button BtnKategori1;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.TextBox Harga;
    private widget.TextBox Kd2;
    private widget.TextBox KdGudang;
    private widget.TextBox Kdbar;
    private widget.TextBox Keterangan;
    private widget.Label LCount;
    private widget.Label LTotal;
    private widget.TextBox Lebih;
    private widget.TextBox NmGudang;
    private widget.TextBox Nmbar;
    private widget.TextBox NomiLebih;
    private widget.TextBox Nominal;
    private widget.TextBox Real;
    private widget.ScrollPane Scroll;
    private widget.TextBox Selisih;
    private widget.TextBox Stok;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.TextBox TotalReal;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPanel jPanel1;
    private widget.TextBox kdgolongan;
    private widget.TextBox kdgudang;
    private widget.TextBox kdjenis;
    private widget.TextBox kdkategori;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label24;
    private widget.Label label32;
    private widget.Label label34;
    private widget.Label label36;
    private widget.Label label37;
    private widget.Label label38;
    private widget.Label label39;
    private widget.Label label9;
    private widget.TextBox nmgolongan;
    private widget.TextBox nmgudang;
    private widget.TextBox nmjns;
    private widget.TextBox nmkategori;
    private widget.TextBox nobatch;
    private widget.TextBox nofaktur;
    private widget.PanelBiasa panelBiasa1;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private widget.Table tbKamar;
    // End of variables declaration//GEN-END:variables

    private void tampil(String jenis) {
        Valid.tabelKosong(tabMode);
        total=0;
        totalreal=0;
        totallebih=0;
        
        //fornas kering
        tabMode.addRow(new Object[]{"","FORNAS KERING","","","","","","","",""});
        getData(jenis," AND d.kode_kategori = 'K01' AND d.kode_sat in ('TAB','CAP','SAC') ");
        //fornas basah
        tabMode.addRow(new Object[]{"","FORNAS BASAH","","","","","","","",""});
        getData(jenis," AND d.kode_kategori = 'K01' AND d.kode_sat in ('AMP','AMP2','VL','SAL','CRM','SYR') ");
        //nonfornas kering
        tabMode.addRow(new Object[]{"","NON FORNAS KERING","","","","","","","",""});
        getData(jenis," AND d.kode_kategori = 'K02' AND d.kode_sat in ('TAB','CAP','SAC') ");
        //nonfornas basah
        tabMode.addRow(new Object[]{"","NON FORNAS BASAH","","","","","","","",""});
        getData(jenis," AND d.kode_kategori = 'K02' AND d.kode_sat in ('AMP','AMP2','VL','SAL','CRM','SYR') ");
        //tidak diatas
        tabMode.addRow(new Object[]{"","TIDAK SEMUANYA","","","","","","","",""});
        getData(jenis," AND d.kode_sat not in ('TAB','CAP','SAC','AMP','AMP2','VL','SAL','CRM','SYR') ");
        
        LCount.setText(""+tabMode.getRowCount());
        LTotal.setText(df2.format(total));
    }

    public void emptTeks() {
        Kdbar.setText("");
        Nmbar.setText("");
        Stok.setText("0");
        Harga.setText("0");
        Real.setText("0");
        Selisih.setText("0");
        Lebih.setText("0");      
        TotalReal.setText("0");
        Keterangan.setText("");   
        nobatch.setText("");   
        nofaktur.setText("");
        Tanggal.setDate(new Date());
        Nominal.setText("0");
        NomiLebih.setText("0");
        Stok.requestFocus();
    }

    
    
    public JTextField getTextField(){
        return Stok;
    }

    public JButton getButton(){
        return BtnKeluar;
    }
    
        
    public void isCek(){
        BtnHapus.setEnabled(akses.getstok_opname_obat());
        BtnPrint.setEnabled(akses.getstok_opname_obat());    
        NmGudang.setText("Depo 1 (IGD)");
    }
    
    private void exportSEPExcelActionPerformed(java.awt.event.ActionEvent evt) throws IOException { 
        File theDir = new File("C:\\DataLaporanSO\\");
        if (!theDir.exists()){
            theDir.mkdirs();
        }
        String excelFileName = "C:\\DataLaporanSO\\Laporan-"+Tgl1.getSelectedItem()+"_"+Tgl2.getSelectedItem()+".xls";//name of excel file
//        String excelFileName = "";
        String sheetName = "Sheet1";//name of sheet
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetName) ;
        
        //Add first row "NO.","NAMA MATKES","SATUAN","ED","HARGA","STOK AWAL","MASUK","KELUAR","SISA","JUMLAH"};
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell00 = row.createCell(0);
        cell00.setCellValue("NO.");
        HSSFCell cell01 = row.createCell(1);
        cell01.setCellValue("NAMA MATKES");
        HSSFCell cell02 = row.createCell(2);
        cell02.setCellValue("SATUAN");
        HSSFCell cell03 = row.createCell(3);
        cell03.setCellValue("ED");
        HSSFCell cell04 = row.createCell(4);
        cell04.setCellValue("HARGA");
        HSSFCell cell05 = row.createCell(5);
        cell05.setCellValue("STOK AWAL");
        HSSFCell cell06 = row.createCell(6);
        cell06.setCellValue("MASUK");
        HSSFCell cell07 = row.createCell(7);
        cell07.setCellValue("KELUAR");
        HSSFCell cell08 = row.createCell(8);
        cell08.setCellValue("SISA");
        HSSFCell cell09 = row.createCell(9);
        cell09.setCellValue("JUMLAH");
        
        int rowNum = 1; int i=0;
        for(i=0;i<tabMode.getRowCount();i++){  
            HSSFRow row1 = sheet.createRow(rowNum);
            HSSFCell cell10 = row1.createCell(0);
            cell10.setCellValue(rowNum);
            HSSFCell cell11 = row1.createCell(1);
            cell11.setCellValue(tabMode.getValueAt(i,1).toString());
            HSSFCell cell12 = row1.createCell(2);
            cell12.setCellValue(tabMode.getValueAt(i,2).toString());
            HSSFCell cell13 = row1.createCell(3);
            cell13.setCellValue(tabMode.getValueAt(i,3).toString());
            HSSFCell cell14 = row1.createCell(4);
            cell14.setCellValue("Rp "+tabMode.getValueAt(i,4).toString());
            HSSFCell cell15 = row1.createCell(5);
            cell15.setCellValue(tabMode.getValueAt(i,5).toString());
            HSSFCell cell16 = row1.createCell(6);
            cell16.setCellValue(tabMode.getValueAt(i,6).toString());
            HSSFCell cell17 = row1.createCell(7);
            cell17.setCellValue(tabMode.getValueAt(i,7).toString());
            HSSFCell cell18 = row1.createCell(8);
            cell18.setCellValue(tabMode.getValueAt(i,8).toString());
            HSSFCell cell19 = row1.createCell(9);
            cell19.setCellValue("Rp "+tabMode.getValueAt(i,9).toString());
            rowNum++;
        }
        
        FileOutputStream fileOut = new FileOutputStream(excelFileName);

        //write this workbook to an Outputstream.
        wb.write(fileOut);
        fileOut.flush();
        fileOut.close();
        System.out.println("Data Stok Opname berhasil diexport di "+excelFileName);
        JOptionPane.showMessageDialog(null,"Data Stok Opname berhasil diexport di "+excelFileName);
    }
    
    private void getData(String jenis, String filter){
        int i = 1;
        try{    
            sql_cond_golongan=(!nmgolongan.getText().equals("")) ? " AND d.kode_golongan = (SELECT kode FROM golongan_barang WHERE nama = '"+nmgolongan.getText().toString()+"') " : "";
            sql_cond_kategori=(!nmkategori.getText().equals("")) ? " AND d.kode_kategori = (SELECT kode FROM kategori_barang WHERE nama = '"+nmkategori.getText().toString()+"') " : "";
            sql_cond_jenis=(!nmjns.getText().equals("")) ? " AND d.kdjns = (SELECT kdjns FROM jenis WHERE nama = '"+nmjns.getText().toString()+"') " : "";
            
            pstampil=koneksi.prepareStatement(
                    "SELECT d.nama_brng, k.satuan, ifnull(d.expire,'') as expire,d.h_beli,d.kode_brng " +
                    "FROM databarang d  " +
                    "INNER JOIN kodesatuan k ON k.kode_sat = d.kode_sat " +
                    "WHERE d.`status`='1' AND d.kode_brng NOT IN ('','B000009066','B000009374') "+
                    sql_cond_golongan +
                    sql_cond_kategori +
                    sql_cond_jenis + filter +
                    " ORDER BY d.nama_brng asc"); 
            try {     
                rstampil=pstampil.executeQuery();
                while(rstampil.next()){     
//                    System.out.println("tanggal "+tanggal);
//                    System.out.println("kode barang "+rstampil.getString("kode_brng"));
                    
//                    stok_awal=Sequel.cariIsiAngka2("SELECT stok_awal FROM riwayat_barang_medis WHERE kode_brng = '"+rstampil.getString("kode_brng")+"' AND tanggal BETWEEN ? AND ?  " +
//                            "AND kd_bangsal = (SELECT kd_bangsal FROM bangsal WHERE nm_bangsal = '"+NmGudang.getText().toString()+"') ORDER BY tanggal ASC, jam ASC LIMIT 1",
//                            Valid.SetTgl(Tgl1.getSelectedItem()+""),Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    
                    if (jenis == "riwayat"){
                        stok_awal=Sequel.cariIsiAngka("SELECT stok_akhir FROM riwayat_barang_medis WHERE kode_brng = '"+rstampil.getString("kode_brng")+"' AND tanggal < '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"'  " +
                            "AND kd_bangsal = (SELECT kd_bangsal FROM bangsal WHERE nm_bangsal = '"+NmGudang.getText().toString()+"') ORDER BY tanggal DESC, jam DESC LIMIT 1");
                    }else{
                        stok_awal=Sequel.cariIsiAngka("SELECT opname.real FROM opname WHERE kode_brng = '"+rstampil.getString("kode_brng")+"' AND tanggal < '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"'  " +
                            "AND kd_bangsal = (SELECT kd_bangsal FROM bangsal WHERE nm_bangsal = '"+NmGudang.getText().toString()+"') ORDER BY tanggal DESC LIMIT 1");
                    }
                    
                    masuk=Sequel.cariIsiAngka2("SELECT SUM(masuk) AS masuk FROM riwayat_barang_medis WHERE kode_brng = '"+rstampil.getString("kode_brng")+"' AND posisi <> 'Opname' AND tanggal BETWEEN ? AND ? "+
                            "AND kd_bangsal = (SELECT kd_bangsal FROM bangsal WHERE nm_bangsal = '"+NmGudang.getText().toString()+"') ",
                            Valid.SetTgl(Tgl1.getSelectedItem()+""),Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    
                    keluar=Sequel.cariIsiAngka2("SELECT SUM(keluar) AS keluar FROM riwayat_barang_medis WHERE kode_brng = '"+rstampil.getString("kode_brng")+"' AND tanggal BETWEEN ? AND ? "+
                            "AND kd_bangsal = (SELECT kd_bangsal FROM bangsal WHERE nm_bangsal = '"+NmGudang.getText().toString()+"') ",
                            Valid.SetTgl(Tgl1.getSelectedItem()+""),Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            
                    sisa=Sequel.cariIsiAngka2("SELECT stok_akhir FROM riwayat_barang_medis WHERE kode_brng = '"+rstampil.getString("kode_brng")+"' AND tanggal BETWEEN ? AND ?  " +
                            "AND kd_bangsal = (SELECT kd_bangsal FROM bangsal WHERE nm_bangsal = '"+NmGudang.getText().toString()+"') ORDER BY tanggal DESC, jam DESC LIMIT 1",
                            Valid.SetTgl(Tgl1.getSelectedItem()+""),Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    
                    if(sisa == 0){
                        sisa = (stok_awal+masuk)-keluar;
                    }
                    
                    jumlah=sisa*rstampil.getDouble("h_beli");
                    Object[] row={"NO.","NAMA MATKES","SATUAN","ED","HARGA","STOK AWAL","MASUK","KELUAR","SISA","JUMLAH"};
                    tabMode.addRow(new Object[]{
                        i,rstampil.getString("nama_brng"),rstampil.getString("satuan"),rstampil.getString("expire"),Valid.SetAngka4(rstampil.getDouble("h_beli")),
                        Valid.SetAngka(stok_awal), Valid.SetAngka(masuk), Valid.SetAngka(keluar), Valid.SetAngka(sisa), Valid.SetAngka4(jumlah)
                    });
                    i++;
                    
                }
            } catch (Exception e) {
               System.out.println("Notif Opname : "+e);
            }finally{
                if(rstampil!=null){
                    rstampil.close();
                }
                if(pstampil!=null){
                    pstampil.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        
        total += jumlah;
    }
}
