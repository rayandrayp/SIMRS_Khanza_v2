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

package laporan;

import bridging.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.net.URI;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import simrskhanza.DlgKamarInap;
import rekammedis.RMRiwayatPerawatan;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author perpustakaan
 */
public final class YanmedBerkasRM extends javax.swing.JDialog {
    private DefaultTableModel tabMode,tabModeInternal;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0,pilihan=1,reply=0,tab=0;
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
    private final DlgDiagnosaPenyakit diagnosa=new DlgDiagnosaPenyakit(null,false);
    private String status_lanjut="",user="",link="";
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public YanmedBerkasRM(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        

        tabMode=new DefaultTableModel(null,new Object[]{
                "Tgl. Setor","No.RM","Nama Pasien","L/P","Umur","MIL AD","PNS AD","KEL AD","MIL LAIN","PNS LAIN","KEL LAIN","JKN","S","MRS","KRS","Angka Lambat","Keterangan",
                "GS Isi","GS Baca","GS Tgl & Jam","GS Stempel & Nama","GS Koreksi","PAM Isi","PAM Baca","PAM Tgl & Jam","PAM Stempel & Nama","PAM Koreksi",
                "PAK Isi","PAK Baca","PAK Tgl & Jam","PAK Stempel & Nama","PAK Koreksi","DP Isi","DP Baca","DP Tgl & Jam","DP Stempel & Nama","DP Koreksi",
                "RO Isi","RO Baca","RO Tgl & Jam","RO Stempel & Nama","RO Koreksi","FE Isi","FE Baca","FE Tgl & Jam","FE Stempel & Nama","FE Koreksi",
                "CPPT Isi","CPPT Baca","CPPTRO Tgl & Jam","CPPT Stempel & Nama","CPPT Koreksi","DS Isi","DS Baca","DS Tgl & Jam","DS Stempel & Nama","DS Koreksi",
                "TL","Ket. Kekurangan","Tgl Setor Kekurangan","Angka Lambat 2","Keterangan","Dari Komdik","Kode","Ruangan","Kode","Nama Dokter","Dx Utama",
                "ICD 10 Kode", "ICD 10 Sesuai", "ICD 10 Tidak Sesuai", "Dx Sekunder", "Tx Operasi", "ICD 9 Kode", "ICD 9 Sesuai", "ICD 9 Tidak Sesuai",
                "Singkatan Kode", "Singkatan Sesuai", "Singkatan Tidak Sesuai","Simbol Kode", "Simbol Sesuai", "Simbol Tidak Sesuai","Bukti Penunjang", 
                "Ket. Pulang", "Tgl Setor Costing", "Tgl Turun dari Costing", "Angka Lambat 3", "Keterangan", "Tgl Setor Ke Filing", "No. SEP", "No. Rawat","ID Laporan"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDataLaporan.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbDataLaporan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDataLaporan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 92; i++) {
            TableColumn column = tbDataLaporan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(67);
            }else if(i==1){
                column.setPreferredWidth(50);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(25);
            }else if(i==4){
                column.setPreferredWidth(35);
            }else if(i==5){
                column.setPreferredWidth(50);
            }else if(i==6){
                column.setPreferredWidth(50);
            }else if(i==7){
                column.setPreferredWidth(50);
            }else if(i==8){
                column.setPreferredWidth(50);
            }else if(i==9){
                column.setPreferredWidth(50);
            }else if(i==10){
                column.setPreferredWidth(50);
            }else if(i==11){
                column.setPreferredWidth(50);
            }else if(i==12){
                column.setPreferredWidth(50);
            }else if(i==13){
                column.setPreferredWidth(67);
            }else if(i==14){
                column.setPreferredWidth(67);
            }else if(i==15){
                column.setPreferredWidth(80);
            }else if(i==16){
                column.setPreferredWidth(150);
            }else if(i==17){
                column.setPreferredWidth(70);
            }else if(i==18){
                column.setPreferredWidth(70);
            }else if(i==19){
                column.setPreferredWidth(70);
            }else if(i==20){
                column.setPreferredWidth(70);
            }else if(i==21){
                column.setPreferredWidth(70);
            }else if(i==22){
                column.setPreferredWidth(70);
            }else if(i==23){
                column.setPreferredWidth(70);
            }else if(i==24){
                column.setPreferredWidth(70);
            }else if(i==25){
                column.setPreferredWidth(70);
            }else if(i==26){
                column.setPreferredWidth(70);
            }else if(i==27){
                column.setPreferredWidth(70);
            }else if(i==28){
                column.setPreferredWidth(70);
            }else if(i==29){
                column.setPreferredWidth(70);
            }else if(i==30){
                column.setPreferredWidth(70);
            }else if(i==31){
                column.setPreferredWidth(70);
            }else if(i==32){
                column.setPreferredWidth(70);
            }else if(i==33){
                column.setPreferredWidth(70);
            }else if(i==34){
                column.setPreferredWidth(70);
            }else if(i==35){
                column.setPreferredWidth(70);
            }else if(i==36){
                column.setPreferredWidth(70);
            }else if(i==37){
                column.setPreferredWidth(70);
            }else if(i==38){
                column.setPreferredWidth(70);
            }else if(i==39){
                column.setPreferredWidth(70);
            }else if(i==40){
                column.setPreferredWidth(70);
            }else if(i==41){
                column.setPreferredWidth(70);
            }else if(i==42){
                column.setPreferredWidth(70);
            }else if(i==43){
                column.setPreferredWidth(70);
            }else if(i==44){
                column.setPreferredWidth(70);
            }else if(i==45){
                column.setPreferredWidth(70);
            }else if(i==46){
                column.setPreferredWidth(70);
            }else if(i==47){
                column.setPreferredWidth(70);
            }else if(i==48){
                column.setPreferredWidth(70);
            }else if(i==49){
                column.setPreferredWidth(70);
            }else if(i==50){
                column.setPreferredWidth(70);
            }else if(i==51){
                column.setPreferredWidth(70);
            }else if(i==52){
                column.setPreferredWidth(70);
            }else if(i==53){
                column.setPreferredWidth(70);
            }else if(i==54){
                column.setPreferredWidth(70);
            }else if(i==55){
                column.setPreferredWidth(70);
            }else if(i==56){
                column.setPreferredWidth(70);
            }else if(i==57){
                column.setPreferredWidth(70);
            }else if(i==58){
                column.setPreferredWidth(100);
            }else if(i==59){
                column.setPreferredWidth(100);
            }else if(i==60){
                column.setPreferredWidth(90);
            }else if(i==61){
                column.setPreferredWidth(120);
            }else if(i==62){
                column.setPreferredWidth(70);
            }else if(i==63){
                column.setPreferredWidth(40);
            }else if(i==64){
                column.setPreferredWidth(80);
            }else if(i==65){
                column.setPreferredWidth(40);
            }else if(i==66){
                column.setPreferredWidth(150);
            }else if(i==67){
                column.setPreferredWidth(150);
            }else if(i==68){
                column.setPreferredWidth(80);
            }else if(i==69){
                column.setPreferredWidth(50);
            }else if(i==70){
                column.setPreferredWidth(50);
            }else if(i==71){
                column.setPreferredWidth(80);
            }else if(i==72){
                column.setPreferredWidth(80);
            }else if(i==73){
                column.setPreferredWidth(50);
            }else if(i==74){
                column.setPreferredWidth(50);
            }else if(i==75){
                column.setPreferredWidth(80);
            }else if(i==76){
                column.setPreferredWidth(80);
            }else if(i==77){
                column.setPreferredWidth(80);
            }else if(i==78){
                column.setPreferredWidth(80);
            }else if(i==79){
                column.setPreferredWidth(80);
            }else if(i==80){
                column.setPreferredWidth(80);
            }else if(i==81){
                column.setPreferredWidth(80);
            }else if(i==82){
                column.setPreferredWidth(120);
            }else if(i==83){
                column.setPreferredWidth(120);
            }else if(i==84){
                column.setPreferredWidth(80);
            }else if(i==85){
                column.setPreferredWidth(80);
            }else if(i==86){
                column.setPreferredWidth(70);
            }else if(i==87){
                column.setPreferredWidth(100);
            }else if(i==88){
                column.setPreferredWidth(80);
            }else if(i==89){
                column.setPreferredWidth(150);
            } else {
                column.setMinWidth(0);
                column.setMaxWidth(0);                
            }
        }
        tbDataLaporan.setDefaultRenderer(Object.class, new WarnaTable());
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        Komdik.setDocument(new batasInput((byte)1).getKata(Komdik));
//        KetKekurangan.setDocument(new batasInput((byte)250).getKata(KetKekurangan));
        
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
            });
            
        }  
        
        diagnosa.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                setNoRm(TNoRM.getText(), TNoRW.getText(), status_lanjut);
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
        
        
        try {
            user=akses.getkode().replace(" ","").substring(0,9);
        } catch (Exception e) {
            user=akses.getkode();
        }
        
        try {
            link=koneksiDB.URLAPIBPJS();
        } catch (Exception e) {
            System.out.println("E : "+e);
        }
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        Scroll1 = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TPasien = new widget.TextBox();
        TNoRW = new widget.TextBox();
        jLabel5 = new widget.Label();
        jLabel22 = new widget.Label();
        KetAngkaLambat1 = new widget.TextBox();
        JK = new widget.TextBox();
        jLabel27 = new widget.Label();
        GolonganPasien = new widget.ComboBox();
        KetKekurangan = new widget.TextBox();
        Kelengkapan = new widget.ComboBox();
        Komdik = new widget.TextBox();
        jLabel41 = new widget.Label();
        KdRuangan = new widget.TextBox();
        NmRuangan = new widget.TextBox();
        LabelPoli4 = new widget.Label();
        jLabel8 = new widget.Label();
        ChkGC_isi = new widget.CekBox();
        jLabel51 = new widget.Label();
        ChkGC_baca = new widget.CekBox();
        ChkGC_tgl = new widget.CekBox();
        jLabel52 = new widget.Label();
        ChkGC_stempel = new widget.CekBox();
        jLabel53 = new widget.Label();
        jLabel54 = new widget.Label();
        ChkGC_koreksi = new widget.CekBox();
        jLabel23 = new widget.Label();
        jLabel25 = new widget.Label();
        ChkPAM_isi = new widget.CekBox();
        ChkPAM_baca = new widget.CekBox();
        jLabel55 = new widget.Label();
        ChkPAM_tgl = new widget.CekBox();
        jLabel56 = new widget.Label();
        ChkPAM_stempel = new widget.CekBox();
        jLabel57 = new widget.Label();
        ChkPAM_koreksi = new widget.CekBox();
        jLabel58 = new widget.Label();
        jLabel59 = new widget.Label();
        jLabel60 = new widget.Label();
        ChkPAK_stempel = new widget.CekBox();
        ChkPAK_isi = new widget.CekBox();
        ChkPAK_koreksi = new widget.CekBox();
        jLabel61 = new widget.Label();
        ChkPAK_tgl = new widget.CekBox();
        ChkPAK_baca = new widget.CekBox();
        jLabel62 = new widget.Label();
        jLabel63 = new widget.Label();
        jLabel64 = new widget.Label();
        ChkDP_isi = new widget.CekBox();
        ChkDP_baca = new widget.CekBox();
        jLabel65 = new widget.Label();
        ChkDP_tgl = new widget.CekBox();
        jLabel66 = new widget.Label();
        ChkDP_koreksi = new widget.CekBox();
        jLabel67 = new widget.Label();
        jLabel68 = new widget.Label();
        ChkDP_stempel = new widget.CekBox();
        jLabel69 = new widget.Label();
        jLabel70 = new widget.Label();
        jLabel71 = new widget.Label();
        ChkRO_stempel = new widget.CekBox();
        ChkRO_baca = new widget.CekBox();
        jLabel72 = new widget.Label();
        jLabel73 = new widget.Label();
        ChkRO_isi = new widget.CekBox();
        ChkRO_koreksi = new widget.CekBox();
        jLabel74 = new widget.Label();
        ChkRO_tgl = new widget.CekBox();
        jLabel75 = new widget.Label();
        jLabel76 = new widget.Label();
        jLabel77 = new widget.Label();
        ChkFE_stempel = new widget.CekBox();
        ChkFE_baca = new widget.CekBox();
        jLabel78 = new widget.Label();
        jLabel79 = new widget.Label();
        jLabel80 = new widget.Label();
        ChkFE_isi = new widget.CekBox();
        jLabel81 = new widget.Label();
        ChkFE_koreksi = new widget.CekBox();
        ChkFE_tgl = new widget.CekBox();
        jLabel82 = new widget.Label();
        jLabel83 = new widget.Label();
        ChkCPPT_isi = new widget.CekBox();
        ChkCPPT_tgl = new widget.CekBox();
        ChkCPPT_koreksi = new widget.CekBox();
        ChkCPPT_baca = new widget.CekBox();
        jLabel84 = new widget.Label();
        ChkCPPT_stempel = new widget.CekBox();
        jLabel85 = new widget.Label();
        jLabel86 = new widget.Label();
        jLabel87 = new widget.Label();
        jLabel88 = new widget.Label();
        ChkSingkatan = new widget.CekBox();
        jLabel89 = new widget.Label();
        jLabel90 = new widget.Label();
        jLabel91 = new widget.Label();
        ChkDS_stempel = new widget.CekBox();
        jLabel92 = new widget.Label();
        jLabel93 = new widget.Label();
        ChkDS_isi = new widget.CekBox();
        ChkDS_tgl = new widget.CekBox();
        ChkDS_baca = new widget.CekBox();
        jLabel94 = new widget.Label();
        TanggalSetor = new widget.Tanggal();
        jLabel29 = new widget.Label();
        Umur = new widget.TextBox();
        jLabel36 = new widget.Label();
        jLabel37 = new widget.Label();
        jLabel95 = new widget.Label();
        jLabel38 = new widget.Label();
        TanggalSetorKekurangan = new widget.Tanggal();
        jLabel39 = new widget.Label();
        AngkaLambat2 = new widget.TextBox();
        jLabel96 = new widget.Label();
        NmDokter = new widget.TextBox();
        KdDokter = new widget.TextBox();
        jLabel97 = new widget.Label();
        btnDiagnosa = new widget.Button();
        NmDiagnosa = new widget.TextBox();
        KdDiagnosa = new widget.TextBox();
        LabelPoli3 = new widget.Label();
        ChkDS_koreksi = new widget.CekBox();
        LabelPoli8 = new widget.Label();
        DiagnosaSekunder = new widget.TextBox();
        LabelPoli9 = new widget.Label();
        KdTindakan = new widget.TextBox();
        NmTindakan = new widget.TextBox();
        btnTindakan = new widget.Button();
        ChkTindakan = new widget.CekBox();
        jLabel98 = new widget.Label();
        Singkatan = new widget.TextBox();
        ChkDiagnosa = new widget.CekBox();
        ChkSimbol = new widget.CekBox();
        Simbol = new widget.TextBox();
        jLabel99 = new widget.Label();
        jLabel100 = new widget.Label();
        BuktiPenunjang = new widget.TextBox();
        jLabel101 = new widget.Label();
        StatusPulang = new widget.ComboBox();
        jLabel40 = new widget.Label();
        TglSetorCosting = new widget.Tanggal();
        TglTurunCosting = new widget.Tanggal();
        jLabel46 = new widget.Label();
        jLabel42 = new widget.Label();
        AngkaLambat3 = new widget.TextBox();
        jLabel28 = new widget.Label();
        jLabel30 = new widget.Label();
        TNoRM = new widget.TextBox();
        jLabel31 = new widget.Label();
        NoSEP = new widget.TextBox();
        AngkaLambat1 = new widget.TextBox();
        KetAngkaLambat2 = new widget.TextBox();
        KetAngkaLambat3 = new widget.TextBox();
        jLabel43 = new widget.Label();
        TglSetorFiling = new widget.Tanggal();
        tgl_krs = new widget.Tanggal();
        tgl_mrs = new widget.Tanggal();
        internalFrame4 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbDataLaporan = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnEdit = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Laporan Berkas RM Yanmed ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        TabRawat.setBackground(new java.awt.Color(255, 255, 254));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(745, 467));
        FormInput.setLayout(null);

        jLabel4.setText("Data Pasien :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 12, 90, 23);

        TPasien.setEditable(false);
        TPasien.setBackground(new java.awt.Color(245, 250, 240));
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(400, 10, 368, 23);

        TNoRW.setEditable(false);
        TNoRW.setHighlighter(null);
        TNoRW.setName("TNoRW"); // NOI18N
        TNoRW.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TNoRWActionPerformed(evt);
            }
        });
        FormInput.add(TNoRW);
        TNoRW.setBounds(210, 10, 180, 23);

        jLabel5.setText("General Consent :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 100, 90, 23);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("ISI");
        jLabel22.setName("jLabel22"); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel22);
        jLabel22.setBounds(113, 100, 20, 23);

        KetAngkaLambat1.setEditable(false);
        KetAngkaLambat1.setBackground(new java.awt.Color(245, 250, 240));
        KetAngkaLambat1.setHighlighter(null);
        KetAngkaLambat1.setName("KetAngkaLambat1"); // NOI18N
        FormInput.add(KetAngkaLambat1);
        KetAngkaLambat1.setBounds(620, 70, 150, 23);

        JK.setEditable(false);
        JK.setBackground(new java.awt.Color(245, 250, 240));
        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N
        FormInput.add(JK);
        JK.setBounds(90, 40, 50, 23);

        jLabel27.setText("Gol. Pasien :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(590, 40, 60, 23);

        GolonganPasien.setEditable(true);
        GolonganPasien.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MIL AD", "PNS AD", "KEL AD", "MIL LAIN", "PNS LAIN", "KEL LAIN", "JKN", "SWASTA" }));
        GolonganPasien.setName("GolonganPasien"); // NOI18N
        GolonganPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GolonganPasienActionPerformed(evt);
            }
        });
        GolonganPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GolonganPasienKeyPressed(evt);
            }
        });
        FormInput.add(GolonganPasien);
        GolonganPasien.setBounds(650, 40, 120, 23);

        KetKekurangan.setHighlighter(null);
        KetKekurangan.setName("KetKekurangan"); // NOI18N
        KetKekurangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetKekuranganKeyPressed(evt);
            }
        });
        FormInput.add(KetKekurangan);
        KetKekurangan.setBounds(560, 140, 210, 23);

        Kelengkapan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Lengkap", "Tidak Lengkap" }));
        Kelengkapan.setName("Kelengkapan"); // NOI18N
        Kelengkapan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelengkapanKeyPressed(evt);
            }
        });
        FormInput.add(Kelengkapan);
        Kelengkapan.setBounds(560, 110, 150, 23);

        Komdik.setHighlighter(null);
        Komdik.setName("Komdik"); // NOI18N
        Komdik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KomdikKeyPressed(evt);
            }
        });
        FormInput.add(Komdik);
        Komdik.setBounds(520, 270, 70, 23);

        jLabel41.setText("Komdik :");
        jLabel41.setName("jLabel41"); // NOI18N
        jLabel41.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel41);
        jLabel41.setBounds(450, 270, 68, 23);

        KdRuangan.setBackground(new java.awt.Color(245, 250, 240));
        KdRuangan.setHighlighter(null);
        KdRuangan.setName("KdRuangan"); // NOI18N
        FormInput.add(KdRuangan);
        KdRuangan.setBounds(520, 300, 65, 23);

        NmRuangan.setEditable(false);
        NmRuangan.setBackground(new java.awt.Color(245, 250, 240));
        NmRuangan.setHighlighter(null);
        NmRuangan.setName("NmRuangan"); // NOI18N
        FormInput.add(NmRuangan);
        NmRuangan.setBounds(590, 300, 180, 23);

        LabelPoli4.setText("Ket. Kekurangan :");
        LabelPoli4.setName("LabelPoli4"); // NOI18N
        FormInput.add(LabelPoli4);
        LabelPoli4.setBounds(470, 140, 87, 23);

        jLabel8.setText(" MRS :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(30, 70, 60, 23);

        ChkGC_isi.setBorder(null);
        ChkGC_isi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkGC_isi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkGC_isi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkGC_isi.setName("ChkGC_isi"); // NOI18N
        FormInput.add(ChkGC_isi);
        ChkGC_isi.setBounds(90, 100, 23, 23);

        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel51.setText("BACA");
        jLabel51.setName("jLabel51"); // NOI18N
        jLabel51.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel51);
        jLabel51.setBounds(163, 100, 30, 23);

        ChkGC_baca.setBorder(null);
        ChkGC_baca.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkGC_baca.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkGC_baca.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkGC_baca.setName("ChkGC_baca"); // NOI18N
        FormInput.add(ChkGC_baca);
        ChkGC_baca.setBounds(140, 100, 23, 23);

        ChkGC_tgl.setBorder(null);
        ChkGC_tgl.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkGC_tgl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkGC_tgl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkGC_tgl.setName("ChkGC_tgl"); // NOI18N
        FormInput.add(ChkGC_tgl);
        ChkGC_tgl.setBounds(200, 100, 23, 23);

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel52.setText("Tgl & Jam");
        jLabel52.setName("jLabel52"); // NOI18N
        jLabel52.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel52);
        jLabel52.setBounds(223, 100, 50, 23);

        ChkGC_stempel.setBorder(null);
        ChkGC_stempel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkGC_stempel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkGC_stempel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkGC_stempel.setName("ChkGC_stempel"); // NOI18N
        FormInput.add(ChkGC_stempel);
        ChkGC_stempel.setBounds(280, 100, 23, 23);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("Stempel & Nama");
        jLabel53.setName("jLabel53"); // NOI18N
        jLabel53.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel53);
        jLabel53.setBounds(303, 100, 80, 23);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("Koreksi");
        jLabel54.setName("jLabel54"); // NOI18N
        jLabel54.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel54);
        jLabel54.setBounds(413, 100, 40, 23);

        ChkGC_koreksi.setBorder(null);
        ChkGC_koreksi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkGC_koreksi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkGC_koreksi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkGC_koreksi.setName("ChkGC_koreksi"); // NOI18N
        FormInput.add(ChkGC_koreksi);
        ChkGC_koreksi.setBounds(390, 100, 23, 23);

        jLabel23.setText("Pengk. Awal Med :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(0, 120, 90, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("ISI");
        jLabel25.setName("jLabel25"); // NOI18N
        jLabel25.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel25);
        jLabel25.setBounds(113, 120, 20, 23);

        ChkPAM_isi.setBorder(null);
        ChkPAM_isi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkPAM_isi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkPAM_isi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkPAM_isi.setName("ChkPAM_isi"); // NOI18N
        FormInput.add(ChkPAM_isi);
        ChkPAM_isi.setBounds(90, 120, 23, 23);

        ChkPAM_baca.setBorder(null);
        ChkPAM_baca.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkPAM_baca.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkPAM_baca.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkPAM_baca.setName("ChkPAM_baca"); // NOI18N
        FormInput.add(ChkPAM_baca);
        ChkPAM_baca.setBounds(140, 120, 23, 23);

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel55.setText("BACA");
        jLabel55.setName("jLabel55"); // NOI18N
        jLabel55.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel55);
        jLabel55.setBounds(163, 120, 30, 23);

        ChkPAM_tgl.setBorder(null);
        ChkPAM_tgl.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkPAM_tgl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkPAM_tgl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkPAM_tgl.setName("ChkPAM_tgl"); // NOI18N
        FormInput.add(ChkPAM_tgl);
        ChkPAM_tgl.setBounds(200, 120, 23, 23);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("Tgl & Jam");
        jLabel56.setName("jLabel56"); // NOI18N
        jLabel56.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel56);
        jLabel56.setBounds(223, 120, 50, 23);

        ChkPAM_stempel.setBorder(null);
        ChkPAM_stempel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkPAM_stempel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkPAM_stempel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkPAM_stempel.setName("ChkPAM_stempel"); // NOI18N
        FormInput.add(ChkPAM_stempel);
        ChkPAM_stempel.setBounds(280, 120, 23, 23);

        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel57.setText("Stempel & Nama");
        jLabel57.setName("jLabel57"); // NOI18N
        jLabel57.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel57);
        jLabel57.setBounds(303, 120, 80, 23);

        ChkPAM_koreksi.setBorder(null);
        ChkPAM_koreksi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkPAM_koreksi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkPAM_koreksi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkPAM_koreksi.setName("ChkPAM_koreksi"); // NOI18N
        FormInput.add(ChkPAM_koreksi);
        ChkPAM_koreksi.setBounds(390, 120, 23, 23);

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel58.setText("Koreksi");
        jLabel58.setName("jLabel58"); // NOI18N
        jLabel58.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel58);
        jLabel58.setBounds(413, 120, 40, 23);

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel59.setText("ISI");
        jLabel59.setName("jLabel59"); // NOI18N
        jLabel59.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel59);
        jLabel59.setBounds(113, 140, 20, 23);

        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel60.setText("BACA");
        jLabel60.setName("jLabel60"); // NOI18N
        jLabel60.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel60);
        jLabel60.setBounds(163, 140, 30, 23);

        ChkPAK_stempel.setBorder(null);
        ChkPAK_stempel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkPAK_stempel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkPAK_stempel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkPAK_stempel.setName("ChkPAK_stempel"); // NOI18N
        FormInput.add(ChkPAK_stempel);
        ChkPAK_stempel.setBounds(280, 140, 23, 23);

        ChkPAK_isi.setBorder(null);
        ChkPAK_isi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkPAK_isi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkPAK_isi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkPAK_isi.setName("ChkPAK_isi"); // NOI18N
        FormInput.add(ChkPAK_isi);
        ChkPAK_isi.setBounds(90, 140, 23, 23);

        ChkPAK_koreksi.setBorder(null);
        ChkPAK_koreksi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkPAK_koreksi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkPAK_koreksi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkPAK_koreksi.setName("ChkPAK_koreksi"); // NOI18N
        FormInput.add(ChkPAK_koreksi);
        ChkPAK_koreksi.setBounds(390, 140, 23, 23);

        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel61.setText("Koreksi");
        jLabel61.setName("jLabel61"); // NOI18N
        jLabel61.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel61);
        jLabel61.setBounds(413, 140, 40, 23);

        ChkPAK_tgl.setBorder(null);
        ChkPAK_tgl.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkPAK_tgl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkPAK_tgl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkPAK_tgl.setName("ChkPAK_tgl"); // NOI18N
        FormInput.add(ChkPAK_tgl);
        ChkPAK_tgl.setBounds(200, 140, 23, 23);

        ChkPAK_baca.setBorder(null);
        ChkPAK_baca.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkPAK_baca.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkPAK_baca.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkPAK_baca.setName("ChkPAK_baca"); // NOI18N
        FormInput.add(ChkPAK_baca);
        ChkPAK_baca.setBounds(140, 140, 23, 23);

        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel62.setText("Tgl & Jam");
        jLabel62.setName("jLabel62"); // NOI18N
        jLabel62.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel62);
        jLabel62.setBounds(223, 140, 50, 23);

        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel63.setText("Stempel & Nama");
        jLabel63.setName("jLabel63"); // NOI18N
        jLabel63.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel63);
        jLabel63.setBounds(303, 140, 80, 23);

        jLabel64.setText("Pengk. Awal Kep :");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(0, 140, 90, 23);

        ChkDP_isi.setBorder(null);
        ChkDP_isi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkDP_isi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkDP_isi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkDP_isi.setName("ChkDP_isi"); // NOI18N
        FormInput.add(ChkDP_isi);
        ChkDP_isi.setBounds(90, 160, 23, 23);

        ChkDP_baca.setBorder(null);
        ChkDP_baca.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkDP_baca.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkDP_baca.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkDP_baca.setName("ChkDP_baca"); // NOI18N
        FormInput.add(ChkDP_baca);
        ChkDP_baca.setBounds(140, 160, 23, 23);

        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel65.setText("ISI");
        jLabel65.setName("jLabel65"); // NOI18N
        jLabel65.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel65);
        jLabel65.setBounds(113, 160, 20, 23);

        ChkDP_tgl.setBorder(null);
        ChkDP_tgl.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkDP_tgl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkDP_tgl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkDP_tgl.setName("ChkDP_tgl"); // NOI18N
        FormInput.add(ChkDP_tgl);
        ChkDP_tgl.setBounds(200, 160, 23, 23);

        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel66.setText("BACA");
        jLabel66.setName("jLabel66"); // NOI18N
        jLabel66.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel66);
        jLabel66.setBounds(163, 160, 30, 23);

        ChkDP_koreksi.setBorder(null);
        ChkDP_koreksi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkDP_koreksi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkDP_koreksi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkDP_koreksi.setName("ChkDP_koreksi"); // NOI18N
        FormInput.add(ChkDP_koreksi);
        ChkDP_koreksi.setBounds(390, 160, 23, 23);

        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel67.setText("Koreksi");
        jLabel67.setName("jLabel67"); // NOI18N
        jLabel67.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel67);
        jLabel67.setBounds(413, 160, 40, 23);

        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel68.setText("Tgl & Jam");
        jLabel68.setName("jLabel68"); // NOI18N
        jLabel68.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel68);
        jLabel68.setBounds(223, 160, 50, 23);

        ChkDP_stempel.setBorder(null);
        ChkDP_stempel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkDP_stempel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkDP_stempel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkDP_stempel.setName("ChkDP_stempel"); // NOI18N
        FormInput.add(ChkDP_stempel);
        ChkDP_stempel.setBounds(280, 160, 23, 23);

        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel69.setText("Stempel & Nama");
        jLabel69.setName("jLabel69"); // NOI18N
        jLabel69.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel69);
        jLabel69.setBounds(303, 160, 80, 23);

        jLabel70.setText("Discharge Plan :");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(0, 160, 90, 23);

        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel71.setText("BACA");
        jLabel71.setName("jLabel71"); // NOI18N
        jLabel71.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel71);
        jLabel71.setBounds(163, 180, 30, 23);

        ChkRO_stempel.setBorder(null);
        ChkRO_stempel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkRO_stempel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkRO_stempel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkRO_stempel.setName("ChkRO_stempel"); // NOI18N
        FormInput.add(ChkRO_stempel);
        ChkRO_stempel.setBounds(280, 180, 23, 23);

        ChkRO_baca.setBorder(null);
        ChkRO_baca.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkRO_baca.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkRO_baca.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkRO_baca.setName("ChkRO_baca"); // NOI18N
        FormInput.add(ChkRO_baca);
        ChkRO_baca.setBounds(140, 180, 23, 23);

        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel72.setText("ISI");
        jLabel72.setName("jLabel72"); // NOI18N
        jLabel72.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel72);
        jLabel72.setBounds(113, 180, 20, 23);

        jLabel73.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel73.setText("Tgl & Jam");
        jLabel73.setName("jLabel73"); // NOI18N
        jLabel73.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel73);
        jLabel73.setBounds(223, 180, 50, 23);

        ChkRO_isi.setBorder(null);
        ChkRO_isi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkRO_isi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkRO_isi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkRO_isi.setName("ChkRO_isi"); // NOI18N
        FormInput.add(ChkRO_isi);
        ChkRO_isi.setBounds(90, 180, 23, 23);

        ChkRO_koreksi.setBorder(null);
        ChkRO_koreksi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkRO_koreksi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkRO_koreksi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkRO_koreksi.setName("ChkRO_koreksi"); // NOI18N
        FormInput.add(ChkRO_koreksi);
        ChkRO_koreksi.setBounds(390, 180, 23, 23);

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel74.setText("Stempel & Nama");
        jLabel74.setName("jLabel74"); // NOI18N
        jLabel74.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel74);
        jLabel74.setBounds(303, 180, 80, 23);

        ChkRO_tgl.setBorder(null);
        ChkRO_tgl.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkRO_tgl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkRO_tgl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkRO_tgl.setName("ChkRO_tgl"); // NOI18N
        FormInput.add(ChkRO_tgl);
        ChkRO_tgl.setBounds(200, 180, 23, 23);

        jLabel75.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel75.setText("Koreksi");
        jLabel75.setName("jLabel75"); // NOI18N
        jLabel75.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel75);
        jLabel75.setBounds(413, 180, 40, 23);

        jLabel76.setText("Rekonsiliasi Obat :");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(0, 180, 90, 23);

        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel77.setText("Tgl & Jam");
        jLabel77.setName("jLabel77"); // NOI18N
        jLabel77.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel77);
        jLabel77.setBounds(223, 200, 50, 23);

        ChkFE_stempel.setBorder(null);
        ChkFE_stempel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkFE_stempel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkFE_stempel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkFE_stempel.setName("ChkFE_stempel"); // NOI18N
        FormInput.add(ChkFE_stempel);
        ChkFE_stempel.setBounds(280, 200, 23, 23);

        ChkFE_baca.setBorder(null);
        ChkFE_baca.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkFE_baca.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkFE_baca.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkFE_baca.setName("ChkFE_baca"); // NOI18N
        FormInput.add(ChkFE_baca);
        ChkFE_baca.setBounds(140, 200, 23, 23);

        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel78.setText("Koreksi");
        jLabel78.setName("jLabel78"); // NOI18N
        jLabel78.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel78);
        jLabel78.setBounds(413, 200, 40, 23);

        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel79.setText("Stempel & Nama");
        jLabel79.setName("jLabel79"); // NOI18N
        jLabel79.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel79);
        jLabel79.setBounds(303, 200, 80, 23);

        jLabel80.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel80.setText("BACA");
        jLabel80.setName("jLabel80"); // NOI18N
        jLabel80.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel80);
        jLabel80.setBounds(163, 200, 30, 23);

        ChkFE_isi.setBorder(null);
        ChkFE_isi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkFE_isi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkFE_isi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkFE_isi.setName("ChkFE_isi"); // NOI18N
        FormInput.add(ChkFE_isi);
        ChkFE_isi.setBounds(90, 200, 23, 23);

        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel81.setText("ISI");
        jLabel81.setName("jLabel81"); // NOI18N
        jLabel81.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel81);
        jLabel81.setBounds(113, 200, 20, 23);

        ChkFE_koreksi.setBorder(null);
        ChkFE_koreksi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkFE_koreksi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkFE_koreksi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkFE_koreksi.setName("ChkFE_koreksi"); // NOI18N
        FormInput.add(ChkFE_koreksi);
        ChkFE_koreksi.setBounds(390, 200, 23, 23);

        ChkFE_tgl.setBorder(null);
        ChkFE_tgl.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkFE_tgl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkFE_tgl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkFE_tgl.setName("ChkFE_tgl"); // NOI18N
        FormInput.add(ChkFE_tgl);
        ChkFE_tgl.setBounds(200, 200, 23, 23);

        jLabel82.setText("Form Edukasi :");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(0, 200, 90, 23);

        jLabel83.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel83.setText("Tgl & Jam");
        jLabel83.setName("jLabel83"); // NOI18N
        jLabel83.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel83);
        jLabel83.setBounds(223, 220, 50, 23);

        ChkCPPT_isi.setBorder(null);
        ChkCPPT_isi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkCPPT_isi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkCPPT_isi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkCPPT_isi.setName("ChkCPPT_isi"); // NOI18N
        FormInput.add(ChkCPPT_isi);
        ChkCPPT_isi.setBounds(90, 220, 23, 23);

        ChkCPPT_tgl.setBorder(null);
        ChkCPPT_tgl.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkCPPT_tgl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkCPPT_tgl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkCPPT_tgl.setName("ChkCPPT_tgl"); // NOI18N
        FormInput.add(ChkCPPT_tgl);
        ChkCPPT_tgl.setBounds(200, 220, 23, 23);

        ChkCPPT_koreksi.setBorder(null);
        ChkCPPT_koreksi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkCPPT_koreksi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkCPPT_koreksi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkCPPT_koreksi.setName("ChkCPPT_koreksi"); // NOI18N
        FormInput.add(ChkCPPT_koreksi);
        ChkCPPT_koreksi.setBounds(390, 220, 23, 23);

        ChkCPPT_baca.setBorder(null);
        ChkCPPT_baca.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkCPPT_baca.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkCPPT_baca.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkCPPT_baca.setName("ChkCPPT_baca"); // NOI18N
        FormInput.add(ChkCPPT_baca);
        ChkCPPT_baca.setBounds(140, 220, 23, 23);

        jLabel84.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel84.setText("Koreksi");
        jLabel84.setName("jLabel84"); // NOI18N
        jLabel84.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel84);
        jLabel84.setBounds(413, 220, 40, 23);

        ChkCPPT_stempel.setBorder(null);
        ChkCPPT_stempel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkCPPT_stempel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkCPPT_stempel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkCPPT_stempel.setName("ChkCPPT_stempel"); // NOI18N
        FormInput.add(ChkCPPT_stempel);
        ChkCPPT_stempel.setBounds(280, 220, 23, 23);

        jLabel85.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel85.setText("ISI");
        jLabel85.setName("jLabel85"); // NOI18N
        jLabel85.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel85);
        jLabel85.setBounds(113, 220, 20, 23);

        jLabel86.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel86.setText("Stempel & Nama");
        jLabel86.setName("jLabel86"); // NOI18N
        jLabel86.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel86);
        jLabel86.setBounds(303, 220, 80, 23);

        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel87.setText("BACA");
        jLabel87.setName("jLabel87"); // NOI18N
        jLabel87.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel87);
        jLabel87.setBounds(163, 220, 30, 23);

        jLabel88.setText("CPPT :");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(0, 220, 90, 23);

        ChkSingkatan.setBorder(null);
        ChkSingkatan.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkSingkatan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkSingkatan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkSingkatan.setName("ChkSingkatan"); // NOI18N
        ChkSingkatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkSingkatanActionPerformed(evt);
            }
        });
        FormInput.add(ChkSingkatan);
        ChkSingkatan.setBounds(160, 360, 23, 23);

        jLabel89.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel89.setText("Stempel & Nama");
        jLabel89.setName("jLabel89"); // NOI18N
        jLabel89.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel89);
        jLabel89.setBounds(303, 240, 80, 23);

        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel90.setText("ISI");
        jLabel90.setName("jLabel90"); // NOI18N
        jLabel90.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel90);
        jLabel90.setBounds(113, 240, 20, 23);

        jLabel91.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel91.setText("Tgl & Jam");
        jLabel91.setName("jLabel91"); // NOI18N
        jLabel91.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel91);
        jLabel91.setBounds(223, 240, 50, 23);

        ChkDS_stempel.setBorder(null);
        ChkDS_stempel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkDS_stempel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkDS_stempel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkDS_stempel.setName("ChkDS_stempel"); // NOI18N
        FormInput.add(ChkDS_stempel);
        ChkDS_stempel.setBounds(280, 240, 23, 23);

        jLabel92.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel92.setText("BACA");
        jLabel92.setName("jLabel92"); // NOI18N
        jLabel92.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel92);
        jLabel92.setBounds(163, 240, 30, 23);

        jLabel93.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel93.setText("Koreksi");
        jLabel93.setName("jLabel93"); // NOI18N
        jLabel93.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel93);
        jLabel93.setBounds(413, 240, 40, 23);

        ChkDS_isi.setBorder(null);
        ChkDS_isi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkDS_isi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkDS_isi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkDS_isi.setName("ChkDS_isi"); // NOI18N
        FormInput.add(ChkDS_isi);
        ChkDS_isi.setBounds(90, 240, 23, 23);

        ChkDS_tgl.setBorder(null);
        ChkDS_tgl.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkDS_tgl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkDS_tgl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkDS_tgl.setName("ChkDS_tgl"); // NOI18N
        FormInput.add(ChkDS_tgl);
        ChkDS_tgl.setBounds(200, 240, 23, 23);

        ChkDS_baca.setBorder(null);
        ChkDS_baca.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkDS_baca.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkDS_baca.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkDS_baca.setName("ChkDS_baca"); // NOI18N
        FormInput.add(ChkDS_baca);
        ChkDS_baca.setBounds(140, 240, 23, 23);

        jLabel94.setText("Discharge Sum :");
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(0, 240, 90, 23);

        TanggalSetor.setForeground(new java.awt.Color(50, 70, 50));
        TanggalSetor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-01-2023" }));
        TanggalSetor.setDisplayFormat("dd-MM-yyyy");
        TanggalSetor.setName("TanggalSetor"); // NOI18N
        TanggalSetor.setOpaque(false);
        TanggalSetor.setPreferredSize(new java.awt.Dimension(95, 23));
        TanggalSetor.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                TanggalSetorPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        TanggalSetor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalSetorKeyPressed(evt);
            }
        });
        FormInput.add(TanggalSetor);
        TanggalSetor.setBounds(390, 70, 90, 23);

        jLabel29.setText("Angk. Lambat :");
        jLabel29.setName("jLabel29"); // NOI18N
        jLabel29.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel29);
        jLabel29.setBounds(485, 70, 80, 23);

        Umur.setEditable(false);
        Umur.setBackground(new java.awt.Color(245, 250, 240));
        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N
        FormInput.add(Umur);
        Umur.setBounds(190, 40, 80, 23);

        jLabel36.setText(" KRS :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(200, 70, 30, 23);

        jLabel37.setText("Tgl. Setor :");
        jLabel37.setName("jLabel37"); // NOI18N
        jLabel37.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel37);
        jLabel37.setBounds(330, 70, 55, 23);

        jLabel95.setText("Kelengkapan :");
        jLabel95.setName("jLabel95"); // NOI18N
        jLabel95.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel95);
        jLabel95.setBounds(480, 110, 80, 23);

        jLabel38.setText("Tgl. Setor Kekurangan :");
        jLabel38.setName("jLabel38"); // NOI18N
        jLabel38.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel38);
        jLabel38.setBounds(450, 170, 140, 23);

        TanggalSetorKekurangan.setForeground(new java.awt.Color(50, 70, 50));
        TanggalSetorKekurangan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-01-2023" }));
        TanggalSetorKekurangan.setDisplayFormat("dd-MM-yyyy");
        TanggalSetorKekurangan.setName("TanggalSetorKekurangan"); // NOI18N
        TanggalSetorKekurangan.setOpaque(false);
        TanggalSetorKekurangan.setPreferredSize(new java.awt.Dimension(95, 23));
        TanggalSetorKekurangan.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                TanggalSetorKekuranganPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        TanggalSetorKekurangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalSetorKekuranganKeyPressed(evt);
            }
        });
        FormInput.add(TanggalSetorKekurangan);
        TanggalSetorKekurangan.setBounds(600, 170, 100, 23);

        jLabel39.setText("Angk. Lambat :");
        jLabel39.setName("jLabel39"); // NOI18N
        jLabel39.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel39);
        jLabel39.setBounds(480, 200, 80, 23);

        AngkaLambat2.setEditable(false);
        AngkaLambat2.setBackground(new java.awt.Color(245, 250, 240));
        AngkaLambat2.setHighlighter(null);
        AngkaLambat2.setName("AngkaLambat2"); // NOI18N
        FormInput.add(AngkaLambat2);
        AngkaLambat2.setBounds(560, 200, 50, 23);

        jLabel96.setText("Ruangan :");
        jLabel96.setName("jLabel96"); // NOI18N
        jLabel96.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel96);
        jLabel96.setBounds(450, 300, 68, 23);

        NmDokter.setEditable(false);
        NmDokter.setBackground(new java.awt.Color(245, 250, 240));
        NmDokter.setHighlighter(null);
        NmDokter.setName("NmDokter"); // NOI18N
        FormInput.add(NmDokter);
        NmDokter.setBounds(590, 330, 180, 23);

        KdDokter.setBackground(new java.awt.Color(245, 250, 240));
        KdDokter.setHighlighter(null);
        KdDokter.setName("KdDokter"); // NOI18N
        FormInput.add(KdDokter);
        KdDokter.setBounds(520, 330, 65, 23);

        jLabel97.setText("Dokter :");
        jLabel97.setName("jLabel97"); // NOI18N
        jLabel97.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel97);
        jLabel97.setBounds(450, 330, 68, 23);

        btnDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDiagnosa.setMnemonic('X');
        btnDiagnosa.setToolTipText("Alt+X");
        btnDiagnosa.setName("btnDiagnosa"); // NOI18N
        btnDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiagnosaActionPerformed(evt);
            }
        });
        btnDiagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDiagnosaKeyPressed(evt);
            }
        });
        FormInput.add(btnDiagnosa);
        btnDiagnosa.setBounds(350, 270, 28, 23);

        NmDiagnosa.setBackground(new java.awt.Color(245, 250, 240));
        NmDiagnosa.setHighlighter(null);
        NmDiagnosa.setName("NmDiagnosa"); // NOI18N
        FormInput.add(NmDiagnosa);
        NmDiagnosa.setBounds(170, 270, 180, 23);

        KdDiagnosa.setBackground(new java.awt.Color(245, 250, 240));
        KdDiagnosa.setHighlighter(null);
        KdDiagnosa.setName("KdDiagnosa"); // NOI18N
        FormInput.add(KdDiagnosa);
        KdDiagnosa.setBounds(90, 270, 75, 23);

        LabelPoli3.setText("Dx Utama :");
        LabelPoli3.setName("LabelPoli3"); // NOI18N
        FormInput.add(LabelPoli3);
        LabelPoli3.setBounds(0, 270, 90, 23);

        ChkDS_koreksi.setBorder(null);
        ChkDS_koreksi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkDS_koreksi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkDS_koreksi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkDS_koreksi.setName("ChkDS_koreksi"); // NOI18N
        FormInput.add(ChkDS_koreksi);
        ChkDS_koreksi.setBounds(390, 240, 23, 23);

        LabelPoli8.setText("Dx Sekunder :");
        LabelPoli8.setName("LabelPoli8"); // NOI18N
        FormInput.add(LabelPoli8);
        LabelPoli8.setBounds(0, 300, 90, 23);

        DiagnosaSekunder.setBackground(new java.awt.Color(245, 250, 240));
        DiagnosaSekunder.setHighlighter(null);
        DiagnosaSekunder.setName("DiagnosaSekunder"); // NOI18N
        FormInput.add(DiagnosaSekunder);
        DiagnosaSekunder.setBounds(90, 300, 260, 23);

        LabelPoli9.setText("Tindakan :");
        LabelPoli9.setName("LabelPoli9"); // NOI18N
        FormInput.add(LabelPoli9);
        LabelPoli9.setBounds(0, 330, 90, 23);

        KdTindakan.setBackground(new java.awt.Color(245, 250, 240));
        KdTindakan.setHighlighter(null);
        KdTindakan.setName("KdTindakan"); // NOI18N
        FormInput.add(KdTindakan);
        KdTindakan.setBounds(90, 330, 75, 23);

        NmTindakan.setBackground(new java.awt.Color(245, 250, 240));
        NmTindakan.setHighlighter(null);
        NmTindakan.setName("NmTindakan"); // NOI18N
        FormInput.add(NmTindakan);
        NmTindakan.setBounds(170, 330, 180, 23);

        btnTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnTindakan.setMnemonic('X');
        btnTindakan.setToolTipText("Alt+X");
        btnTindakan.setName("btnTindakan"); // NOI18N
        btnTindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTindakanActionPerformed(evt);
            }
        });
        btnTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnTindakanKeyPressed(evt);
            }
        });
        FormInput.add(btnTindakan);
        btnTindakan.setBounds(350, 330, 28, 23);

        ChkTindakan.setBorder(null);
        ChkTindakan.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkTindakan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkTindakan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkTindakan.setName("ChkTindakan"); // NOI18N
        FormInput.add(ChkTindakan);
        ChkTindakan.setBounds(380, 330, 23, 23);

        jLabel98.setText("Singkatan :");
        jLabel98.setName("jLabel98"); // NOI18N
        jLabel98.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel98);
        jLabel98.setBounds(20, 360, 68, 23);

        Singkatan.setBackground(new java.awt.Color(245, 250, 240));
        Singkatan.setHighlighter(null);
        Singkatan.setName("Singkatan"); // NOI18N
        FormInput.add(Singkatan);
        Singkatan.setBounds(90, 360, 65, 23);

        ChkDiagnosa.setBorder(null);
        ChkDiagnosa.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkDiagnosa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkDiagnosa.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkDiagnosa.setName("ChkDiagnosa"); // NOI18N
        FormInput.add(ChkDiagnosa);
        ChkDiagnosa.setBounds(380, 270, 23, 23);

        ChkSimbol.setBorder(null);
        ChkSimbol.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkSimbol.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkSimbol.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkSimbol.setName("ChkSimbol"); // NOI18N
        FormInput.add(ChkSimbol);
        ChkSimbol.setBounds(320, 360, 23, 23);

        Simbol.setBackground(new java.awt.Color(245, 250, 240));
        Simbol.setHighlighter(null);
        Simbol.setName("Simbol"); // NOI18N
        FormInput.add(Simbol);
        Simbol.setBounds(250, 360, 65, 23);

        jLabel99.setText("Simbol :");
        jLabel99.setName("jLabel99"); // NOI18N
        jLabel99.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel99);
        jLabel99.setBounds(200, 360, 50, 23);

        jLabel100.setText("Bukti Penunjang :");
        jLabel100.setName("jLabel100"); // NOI18N
        jLabel100.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel100);
        jLabel100.setBounds(430, 360, 90, 23);

        BuktiPenunjang.setBackground(new java.awt.Color(245, 250, 240));
        BuktiPenunjang.setHighlighter(null);
        BuktiPenunjang.setName("BuktiPenunjang"); // NOI18N
        FormInput.add(BuktiPenunjang);
        BuktiPenunjang.setBounds(520, 360, 220, 23);

        jLabel101.setText("Status Pulang :");
        jLabel101.setName("jLabel101"); // NOI18N
        jLabel101.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel101);
        jLabel101.setBounds(430, 390, 90, 23);

        StatusPulang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Atas Persetujuan Dokter", "Atas Permintaan Sendiri", "Sehat", "Rujuk", "Meninggal", "Sembuh", "Membaik", "Pulang Paksa", "Pindah Kamar", "Status Belum Lengkap", "Atas Persetujuan Dokter", "Atas Permintaan Sendiri", "Lain-lain" }));
        StatusPulang.setName("StatusPulang"); // NOI18N
        StatusPulang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                StatusPulangItemStateChanged(evt);
            }
        });
        StatusPulang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusPulangKeyPressed(evt);
            }
        });
        FormInput.add(StatusPulang);
        StatusPulang.setBounds(520, 390, 190, 23);

        jLabel40.setText("Setor Costing :");
        jLabel40.setName("jLabel40"); // NOI18N
        jLabel40.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel40);
        jLabel40.setBounds(0, 390, 90, 23);

        TglSetorCosting.setForeground(new java.awt.Color(50, 70, 50));
        TglSetorCosting.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-01-2023" }));
        TglSetorCosting.setDisplayFormat("dd-MM-yyyy");
        TglSetorCosting.setName("TglSetorCosting"); // NOI18N
        TglSetorCosting.setOpaque(false);
        TglSetorCosting.setPreferredSize(new java.awt.Dimension(95, 23));
        TglSetorCosting.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                TglSetorCostingPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        TglSetorCosting.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglSetorCostingKeyPressed(evt);
            }
        });
        FormInput.add(TglSetorCosting);
        TglSetorCosting.setBounds(90, 390, 100, 23);

        TglTurunCosting.setForeground(new java.awt.Color(50, 70, 50));
        TglTurunCosting.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-01-2023" }));
        TglTurunCosting.setDisplayFormat("dd-MM-yyyy");
        TglTurunCosting.setName("TglTurunCosting"); // NOI18N
        TglTurunCosting.setOpaque(false);
        TglTurunCosting.setPreferredSize(new java.awt.Dimension(95, 23));
        TglTurunCosting.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                TglTurunCostingPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        TglTurunCosting.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglTurunCostingKeyPressed(evt);
            }
        });
        FormInput.add(TglTurunCosting);
        TglTurunCosting.setBounds(300, 390, 100, 23);

        jLabel46.setText("Turun Costing :");
        jLabel46.setName("jLabel46"); // NOI18N
        jLabel46.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel46);
        jLabel46.setBounds(200, 390, 90, 23);

        jLabel42.setText("Angk. Lambat :");
        jLabel42.setName("jLabel42"); // NOI18N
        jLabel42.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel42);
        jLabel42.setBounds(10, 420, 80, 23);

        AngkaLambat3.setEditable(false);
        AngkaLambat3.setBackground(new java.awt.Color(245, 250, 240));
        AngkaLambat3.setHighlighter(null);
        AngkaLambat3.setName("AngkaLambat3"); // NOI18N
        FormInput.add(AngkaLambat3);
        AngkaLambat3.setBounds(90, 420, 50, 23);

        jLabel28.setText("Umur :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(150, 40, 40, 23);

        jLabel30.setText("Jenis Kelamin :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(0, 40, 90, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TNoRMActionPerformed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(90, 10, 110, 23);

        jLabel31.setText("No. SEP :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(280, 40, 50, 23);

        NoSEP.setBackground(new java.awt.Color(245, 250, 240));
        NoSEP.setHighlighter(null);
        NoSEP.setName("NoSEP"); // NOI18N
        FormInput.add(NoSEP);
        NoSEP.setBounds(330, 40, 250, 23);

        AngkaLambat1.setEditable(false);
        AngkaLambat1.setBackground(new java.awt.Color(245, 250, 240));
        AngkaLambat1.setHighlighter(null);
        AngkaLambat1.setName("AngkaLambat1"); // NOI18N
        FormInput.add(AngkaLambat1);
        AngkaLambat1.setBounds(565, 70, 50, 23);

        KetAngkaLambat2.setEditable(false);
        KetAngkaLambat2.setBackground(new java.awt.Color(245, 250, 240));
        KetAngkaLambat2.setHighlighter(null);
        KetAngkaLambat2.setName("KetAngkaLambat2"); // NOI18N
        KetAngkaLambat2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KetAngkaLambat2ActionPerformed(evt);
            }
        });
        FormInput.add(KetAngkaLambat2);
        KetAngkaLambat2.setBounds(610, 200, 160, 23);

        KetAngkaLambat3.setEditable(false);
        KetAngkaLambat3.setBackground(new java.awt.Color(245, 250, 240));
        KetAngkaLambat3.setHighlighter(null);
        KetAngkaLambat3.setName("KetAngkaLambat3"); // NOI18N
        KetAngkaLambat3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KetAngkaLambat3ActionPerformed(evt);
            }
        });
        FormInput.add(KetAngkaLambat3);
        KetAngkaLambat3.setBounds(150, 420, 170, 23);

        jLabel43.setText("Tgl. Setor Filing :");
        jLabel43.setName("jLabel43"); // NOI18N
        jLabel43.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel43);
        jLabel43.setBounds(430, 420, 90, 23);

        TglSetorFiling.setForeground(new java.awt.Color(50, 70, 50));
        TglSetorFiling.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-01-2023" }));
        TglSetorFiling.setDisplayFormat("dd-MM-yyyy");
        TglSetorFiling.setName("TglSetorFiling"); // NOI18N
        TglSetorFiling.setOpaque(false);
        TglSetorFiling.setPreferredSize(new java.awt.Dimension(95, 23));
        TglSetorFiling.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglSetorFilingKeyPressed(evt);
            }
        });
        FormInput.add(TglSetorFiling);
        TglSetorFiling.setBounds(520, 420, 100, 23);

        tgl_krs.setForeground(new java.awt.Color(50, 70, 50));
        tgl_krs.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-01-2023" }));
        tgl_krs.setDisplayFormat("dd-MM-yyyy");
        tgl_krs.setName("tgl_krs"); // NOI18N
        tgl_krs.setOpaque(false);
        tgl_krs.setPreferredSize(new java.awt.Dimension(95, 23));
        tgl_krs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tgl_krsKeyPressed(evt);
            }
        });
        FormInput.add(tgl_krs);
        tgl_krs.setBounds(230, 70, 90, 23);

        tgl_mrs.setForeground(new java.awt.Color(50, 70, 50));
        tgl_mrs.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-01-2023" }));
        tgl_mrs.setDisplayFormat("dd-MM-yyyy");
        tgl_mrs.setName("tgl_mrs"); // NOI18N
        tgl_mrs.setOpaque(false);
        tgl_mrs.setPreferredSize(new java.awt.Dimension(95, 23));
        tgl_mrs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tgl_mrsKeyPressed(evt);
            }
        });
        FormInput.add(tgl_mrs);
        tgl_mrs.setBounds(90, 70, 90, 23);

        Scroll1.setViewportView(FormInput);

        internalFrame2.add(Scroll1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Laporan", internalFrame2);

        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbDataLaporan.setAutoCreateRowSorter(true);
        tbDataLaporan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDataLaporan.setName("tbDataLaporan"); // NOI18N
        tbDataLaporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataLaporanMouseClicked(evt);
            }
        });
        tbDataLaporan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDataLaporanKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbDataLaporan);

        internalFrame4.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setBorder(null);
        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl. SEP :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-01-2023" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-01-2023" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(205, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
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
        panelGlass9.add(BtnCari);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(LCount);

        internalFrame4.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        TabRawat.addTab("Data Laporan", internalFrame4);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);
        TabRawat.getAccessibleContext().setAccessibleName("Input Laporan");
        TabRawat.getAccessibleContext().setAccessibleDescription("");

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnBatal);

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
        panelGlass8.add(BtnHapus);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/download24.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Download");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setMaximumSize(new java.awt.Dimension(130, 33));
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(150, 30));
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
        panelGlass8.add(BtnPrint);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(100, 30));
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
        panelGlass8.add(BtnAll);

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
        panelGlass8.add(BtnKeluar);

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (TNoRW.getText().trim().equals("")||TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRW, "Pasien");
        }else{  
            insertData();         
        }   
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnSimpan,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(TabRawat.getSelectedIndex()==1){
            if(tbDataLaporan.getSelectedRow()!= -1){
                try {
                    deleteData();
                }catch (Exception ex) {
                    System.out.println("Notifikasi Bridging Hapus : "+ex);
                }
            }else{
                JOptionPane.showMessageDialog(null,"Silahkan pilih dulu data yang mau dihapus..!!");
            }
        }
                       
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(tab==0){
            if(tbDataLaporan.getSelectedRow()!= -1){
                if (TNoRW.getText().trim().equals("")||TPasien.getText().trim().equals("")) {
                    Valid.textKosong(TNoRW, "Pasien");
                }else{
                    Sequel.mengedit("laporan_berkasrm",
                        "id=?","tgl_setor=?,no_rkm_medis=?,no_rawat=?,nm_pasien=?,jk=?,umur=?,golongan_pasien=?,tgl_mrs=?,tgl_krs=?,angka_lambat1=?,ket_angka_lambat1=?,"+
                        "gc_isi=?,gc_baca=?,gc_tgl=?,gc_stempel=?,gc_koreksi=?,pam_isi=?,pam_baca=?,pam_tgl=?,pam_stempel=?,pam_koreksi=?,pak_isi=?,pak_baca=?,pak_tgl=?,pak_stempel=?,pak_koreksi=?,"+
                        "dp_isi=?,dp_baca=?,dp_tgl=?,dp_stempel=?,dp_koreksi=?,ro_isi=?,ro_baca=?,ro_tgl=?,ro_stempel=?,ro_koreksi=?,fe_isi=?,fe_baca=?,fe_tgl=?,fe_stempel=?,fe_koreksi=?,"+
                        "cppt_isi=?,cppt_baca=?,cppt_tgl=?,cppt_stempel=?,cppt_koreksi=?,ds_isi=?,ds_baca=?,ds_tgl=?,ds_stempel=?,ds_koreksi=?,kelengkapan=?,ket_kekurangan=?,tgl_setor_kekurangan=?,"+
                        "angka_lambat2=?,ket_angka_lambat2=?,komdik=?,kd_ruangan=?,nm_ruangan=?,kd_dokter=?,nm_dokter=?,kd_diagnosa=?,nm_diagnosa=?,kesesuaian_diagnosa=?,diagnosa_sekunder=?,"+
                        "kd_icd9=?,nm_icd9=?,kesesuaian_icd9=?,singkatan=?,kesesuaian_singkatan=?,simbol=?,kesesuaian_simbol=?,bukti_penunjang=?,status_pulang=?,tgl_setor_costing=?,tgl_turun_costing=?,"+
                        "angka_lambat3=?,ket_angka_lambat3=?,tgl_setor_filing=?,no_sep=?",81,new String[]{
                        Valid.SetTgl(TanggalSetor.getSelectedItem()+""),TNoRM.getText(),TNoRW.getText(),TPasien.getText(),JK.getText(),Umur.getText(),GolonganPasien.getSelectedItem().toString(),
                        Valid.SetTgl(tgl_mrs.getSelectedItem()+""),Valid.SetTgl(tgl_krs.getSelectedItem()+""),AngkaLambat1.getText(),KetAngkaLambat1.getText(),
                        (ChkGC_isi.isSelected())?"1":"0",(ChkGC_baca.isSelected())?"1":"0",(ChkGC_tgl.isSelected())?"1":"0",(ChkGC_stempel.isSelected())?"1":"0",(ChkGC_koreksi.isSelected())?"1":"0",
                        (ChkPAM_isi.isSelected())?"1":"0",(ChkPAM_baca.isSelected())?"1":"0",(ChkPAM_tgl.isSelected())?"1":"0",(ChkPAM_stempel.isSelected())?"1":"0",(ChkPAM_koreksi.isSelected())?"1":"0",
                        (ChkPAK_isi.isSelected())?"1":"0",(ChkPAK_baca.isSelected())?"1":"0",(ChkPAK_tgl.isSelected())?"1":"0",(ChkPAK_stempel.isSelected())?"1":"0",(ChkPAK_koreksi.isSelected())?"1":"0",
                        (ChkDP_isi.isSelected())?"1":"0",(ChkDP_baca.isSelected())?"1":"0",(ChkDP_tgl.isSelected())?"1":"0",(ChkDP_stempel.isSelected())?"1":"0",(ChkDP_koreksi.isSelected())?"1":"0",
                        (ChkRO_isi.isSelected())?"1":"0",(ChkRO_baca.isSelected())?"1":"0",(ChkRO_tgl.isSelected())?"1":"0",(ChkRO_stempel.isSelected())?"1":"0",(ChkRO_koreksi.isSelected())?"1":"0",
                        (ChkFE_isi.isSelected())?"1":"0",(ChkFE_baca.isSelected())?"1":"0",(ChkFE_tgl.isSelected())?"1":"0",(ChkFE_stempel.isSelected())?"1":"0",(ChkFE_koreksi.isSelected())?"1":"0",
                        (ChkCPPT_isi.isSelected())?"1":"0",(ChkCPPT_baca.isSelected())?"1":"0",(ChkCPPT_tgl.isSelected())?"1":"0",(ChkCPPT_stempel.isSelected())?"1":"0",(ChkCPPT_koreksi.isSelected())?"1":"0",
                        (ChkDS_isi.isSelected())?"1":"0",(ChkDS_baca.isSelected())?"1":"0",(ChkDS_tgl.isSelected())?"1":"0",(ChkDS_stempel.isSelected())?"1":"0",(ChkDS_koreksi.isSelected())?"1":"0",
                        (Kelengkapan.getSelectedItem()=="Lengkap")?"L":"TL",KetKekurangan.getText(),Valid.SetTgl(TanggalSetorKekurangan.getSelectedItem()+""),AngkaLambat2.getText(),KetAngkaLambat2.getText(),
                        Komdik.getText(),KdRuangan.getText(),NmRuangan.getText(),KdDokter.getText(),NmDokter.getText(),KdDiagnosa.getText(),NmDiagnosa.getText(),(ChkDiagnosa.isSelected())?"1":"0", 
                        DiagnosaSekunder.getText(),KdTindakan.getText(),NmTindakan.getText(),(ChkTindakan.isSelected())?"1":"0",Singkatan.getText(),(ChkSingkatan.isSelected())?"1":"0",Simbol.getText(),(ChkSimbol.isSelected())?"1":"0",
                        BuktiPenunjang.getText(),StatusPulang.getSelectedItem().toString(),Valid.SetTgl(TglSetorCosting.getSelectedItem()+""),Valid.SetTgl(TglTurunCosting.getSelectedItem()+""),
                        AngkaLambat3.getText(),KetAngkaLambat3.getText(),Valid.SetTgl(TglSetorFiling.getSelectedItem()+""),NoSEP.getText(),tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),91).toString()
                    });
                    emptTeks(); 
                }
            }else{
                JOptionPane.showMessageDialog(null,"Silahkan pilih dulu data yang mau diganti..!!");
            } 
        }else if(tab==2){
            JOptionPane.showMessageDialog(null,"SEP Internal tidak bisa diubah...!!!");
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(TabRawat.getSelectedIndex()==1){
            if(tabMode.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                TCari.requestFocus();
            }else if(tabMode.getRowCount()!=0){            
                try {
                    exportSEPExcelActionPerformed(evt);
                } catch (IOException ex) {
                    Logger.getLogger(YanmedBerkasRM.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
            
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        if(TabRawat.getSelectedIndex()==1){
            TCari.setText("");
            tampil();
        }
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(akses.getform().equals("DlgReg")||akses.getform().equals("DlgIGD")||akses.getform().equals("DlgKasirRalan")||akses.getform().equals("DlgKamarInap")){
            
        }
    }//GEN-LAST:event_formWindowOpened
    
    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
    }//GEN-LAST:event_BtnCariActionPerformed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void tbDataLaporanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDataLaporanKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbDataLaporanKeyPressed

    private void tbDataLaporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataLaporanMouseClicked
        if(tabMode.getRowCount()!=0){
            getData();
        }
    }//GEN-LAST:event_tbDataLaporanMouseClicked

    private void KomdikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KomdikKeyPressed
        
    }//GEN-LAST:event_KomdikKeyPressed

    private void KelengkapanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelengkapanKeyPressed
        
    }//GEN-LAST:event_KelengkapanKeyPressed

    private void KetKekuranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetKekuranganKeyPressed
        
    }//GEN-LAST:event_KetKekuranganKeyPressed

    private void GolonganPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GolonganPasienKeyPressed
        
    }//GEN-LAST:event_GolonganPasienKeyPressed

    private void TNoRWActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TNoRWActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNoRWActionPerformed

    private void TanggalSetorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalSetorKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalSetorKeyPressed

    private void TanggalSetorKekuranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalSetorKekuranganKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalSetorKekuranganKeyPressed

    private void btnDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiagnosaActionPerformed
        pilihan=1;
        diagnosa.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        diagnosa.setLocationRelativeTo(internalFrame1);
        diagnosa.isCek();
        Date tgl_registrasi = Valid.SetTgl2(Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat=?",TNoRW.getText())+"");
        diagnosa.setNoRm(TNoRW.getText(),tgl_registrasi,tgl_registrasi,status_lanjut);
        diagnosa.panelDiagnosa1.tampil();
        diagnosa.setVisible(true);
    }//GEN-LAST:event_btnDiagnosaActionPerformed

    private void btnDiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDiagnosaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDiagnosaKeyPressed

    private void btnTindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTindakanActionPerformed
        pilihan=1;
        diagnosa.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        diagnosa.setLocationRelativeTo(internalFrame1);
        diagnosa.isCek();
        Date tgl_registrasi = Valid.SetTgl2(Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat=?",TNoRW.getText())+"");
        diagnosa.setNoRm(TNoRW.getText(),tgl_registrasi,tgl_registrasi,status_lanjut);
        diagnosa.panelDiagnosa1.tampil();
        diagnosa.setVisible(true);
    }//GEN-LAST:event_btnTindakanActionPerformed

    private void btnTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnTindakanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTindakanKeyPressed

    private void StatusPulangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_StatusPulangItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_StatusPulangItemStateChanged

    private void StatusPulangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusPulangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_StatusPulangKeyPressed

    private void TglSetorCostingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglSetorCostingKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglSetorCostingKeyPressed

    private void TglTurunCostingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglTurunCostingKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglTurunCostingKeyPressed

    private void TNoRMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TNoRMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNoRMActionPerformed

    private void ChkSingkatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkSingkatanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkSingkatanActionPerformed

    private void KetAngkaLambat2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KetAngkaLambat2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetAngkaLambat2ActionPerformed

    private void KetAngkaLambat3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KetAngkaLambat3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KetAngkaLambat3ActionPerformed

    private void TglSetorFilingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglSetorFilingKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglSetorFilingKeyPressed

    private void GolonganPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GolonganPasienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GolonganPasienActionPerformed

    private void tgl_krsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tgl_krsKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tgl_krsKeyPressed

    private void tgl_mrsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tgl_mrsKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tgl_mrsKeyPressed

    private void TanggalSetorPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_TanggalSetorPopupMenuWillBecomeInvisible
        hitungAngkaLambat1();
    }//GEN-LAST:event_TanggalSetorPopupMenuWillBecomeInvisible

    private void TanggalSetorKekuranganPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_TanggalSetorKekuranganPopupMenuWillBecomeInvisible
        hitungAngkaLambat2();
    }//GEN-LAST:event_TanggalSetorKekuranganPopupMenuWillBecomeInvisible

    private void TglTurunCostingPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_TglTurunCostingPopupMenuWillBecomeInvisible
        hitungAngkaLambat3();
    }//GEN-LAST:event_TglTurunCostingPopupMenuWillBecomeInvisible

    private void TglSetorCostingPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_TglSetorCostingPopupMenuWillBecomeInvisible
        hitungAngkaLambat3();
    }//GEN-LAST:event_TglSetorCostingPopupMenuWillBecomeInvisible

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            YanmedBerkasRM dialog = new YanmedBerkasRM(new javax.swing.JFrame(), true);
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
    private widget.TextBox AngkaLambat1;
    private widget.TextBox AngkaLambat2;
    private widget.TextBox AngkaLambat3;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.TextBox BuktiPenunjang;
    private widget.CekBox ChkCPPT_baca;
    private widget.CekBox ChkCPPT_isi;
    private widget.CekBox ChkCPPT_koreksi;
    private widget.CekBox ChkCPPT_stempel;
    private widget.CekBox ChkCPPT_tgl;
    private widget.CekBox ChkDP_baca;
    private widget.CekBox ChkDP_isi;
    private widget.CekBox ChkDP_koreksi;
    private widget.CekBox ChkDP_stempel;
    private widget.CekBox ChkDP_tgl;
    private widget.CekBox ChkDS_baca;
    private widget.CekBox ChkDS_isi;
    private widget.CekBox ChkDS_koreksi;
    private widget.CekBox ChkDS_stempel;
    private widget.CekBox ChkDS_tgl;
    private widget.CekBox ChkDiagnosa;
    private widget.CekBox ChkFE_baca;
    private widget.CekBox ChkFE_isi;
    private widget.CekBox ChkFE_koreksi;
    private widget.CekBox ChkFE_stempel;
    private widget.CekBox ChkFE_tgl;
    private widget.CekBox ChkGC_baca;
    private widget.CekBox ChkGC_isi;
    private widget.CekBox ChkGC_koreksi;
    private widget.CekBox ChkGC_stempel;
    private widget.CekBox ChkGC_tgl;
    private widget.CekBox ChkPAK_baca;
    private widget.CekBox ChkPAK_isi;
    private widget.CekBox ChkPAK_koreksi;
    private widget.CekBox ChkPAK_stempel;
    private widget.CekBox ChkPAK_tgl;
    private widget.CekBox ChkPAM_baca;
    private widget.CekBox ChkPAM_isi;
    private widget.CekBox ChkPAM_koreksi;
    private widget.CekBox ChkPAM_stempel;
    private widget.CekBox ChkPAM_tgl;
    private widget.CekBox ChkRO_baca;
    private widget.CekBox ChkRO_isi;
    private widget.CekBox ChkRO_koreksi;
    private widget.CekBox ChkRO_stempel;
    private widget.CekBox ChkRO_tgl;
    private widget.CekBox ChkSimbol;
    private widget.CekBox ChkSingkatan;
    private widget.CekBox ChkTindakan;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox DiagnosaSekunder;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox GolonganPasien;
    private widget.TextBox JK;
    private widget.TextBox KdDiagnosa;
    private widget.TextBox KdDokter;
    private widget.TextBox KdRuangan;
    private widget.TextBox KdTindakan;
    private widget.ComboBox Kelengkapan;
    private widget.TextBox KetAngkaLambat1;
    private widget.TextBox KetAngkaLambat2;
    private widget.TextBox KetAngkaLambat3;
    private widget.TextBox KetKekurangan;
    private widget.TextBox Komdik;
    private widget.Label LCount;
    private widget.Label LabelPoli3;
    private widget.Label LabelPoli4;
    private widget.Label LabelPoli8;
    private widget.Label LabelPoli9;
    private widget.TextBox NmDiagnosa;
    private widget.TextBox NmDokter;
    private widget.TextBox NmRuangan;
    private widget.TextBox NmTindakan;
    private widget.TextBox NoSEP;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.TextBox Simbol;
    private widget.TextBox Singkatan;
    private widget.ComboBox StatusPulang;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRW;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TanggalSetor;
    private widget.Tanggal TanggalSetorKekurangan;
    private widget.Tanggal TglSetorCosting;
    private widget.Tanggal TglSetorFiling;
    private widget.Tanggal TglTurunCosting;
    private widget.TextBox Umur;
    private widget.Button btnDiagnosa;
    private widget.Button btnTindakan;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame4;
    private widget.Label jLabel100;
    private widget.Label jLabel101;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel25;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel4;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel46;
    private widget.Label jLabel5;
    private widget.Label jLabel51;
    private widget.Label jLabel52;
    private widget.Label jLabel53;
    private widget.Label jLabel54;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel57;
    private widget.Label jLabel58;
    private widget.Label jLabel59;
    private widget.Label jLabel6;
    private widget.Label jLabel60;
    private widget.Label jLabel61;
    private widget.Label jLabel62;
    private widget.Label jLabel63;
    private widget.Label jLabel64;
    private widget.Label jLabel65;
    private widget.Label jLabel66;
    private widget.Label jLabel67;
    private widget.Label jLabel68;
    private widget.Label jLabel69;
    private widget.Label jLabel7;
    private widget.Label jLabel70;
    private widget.Label jLabel71;
    private widget.Label jLabel72;
    private widget.Label jLabel73;
    private widget.Label jLabel74;
    private widget.Label jLabel75;
    private widget.Label jLabel76;
    private widget.Label jLabel77;
    private widget.Label jLabel78;
    private widget.Label jLabel79;
    private widget.Label jLabel8;
    private widget.Label jLabel80;
    private widget.Label jLabel81;
    private widget.Label jLabel82;
    private widget.Label jLabel83;
    private widget.Label jLabel84;
    private widget.Label jLabel85;
    private widget.Label jLabel86;
    private widget.Label jLabel87;
    private widget.Label jLabel88;
    private widget.Label jLabel89;
    private widget.Label jLabel90;
    private widget.Label jLabel91;
    private widget.Label jLabel92;
    private widget.Label jLabel93;
    private widget.Label jLabel94;
    private widget.Label jLabel95;
    private widget.Label jLabel96;
    private widget.Label jLabel97;
    private widget.Label jLabel98;
    private widget.Label jLabel99;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbDataLaporan;
    private widget.Tanggal tgl_krs;
    private widget.Tanggal tgl_mrs;
    // End of variables declaration//GEN-END:variables
    
    public void tampil() {        
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                    "select * from laporan_berkasrm "+
                    "where tgl_setor between ? and ? "+(TCari.getText().trim().equals("")?"":" and (no_rkm_medis like ? or "+
                    "no_rawat like ? or nm_pasien like ? or no_sep like ? or golongan_pasien like ? or "+
                    "kd_diagnosa like ? or nm_diagnosa like ? or diagnosa_sekunder like ? or kd_icd9 like ? or "+
                    "nm_icd9 like ? or singkatan like ? or simbol like ? or kd_ruangan like ? or nm_ruangan like ? or kd_dokter like ? or "+
                    "nm_dokter like ? or bukti_penunjang like ? or status_pulang like ? ) ")+" order by tgl_setor desc");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,"%"+TCari.getText().trim()+"%");
                    ps.setString(8,"%"+TCari.getText().trim()+"%");
                    ps.setString(9,"%"+TCari.getText().trim()+"%");
                    ps.setString(10,"%"+TCari.getText().trim()+"%");
                    ps.setString(11,"%"+TCari.getText().trim()+"%");
                    ps.setString(12,"%"+TCari.getText().trim()+"%");
                    ps.setString(13,"%"+TCari.getText().trim()+"%");
                    ps.setString(14,"%"+TCari.getText().trim()+"%");
                    ps.setString(15,"%"+TCari.getText().trim()+"%");
                    ps.setString(16,"%"+TCari.getText().trim()+"%");
                    ps.setString(17,"%"+TCari.getText().trim()+"%");
                    ps.setString(18,"%"+TCari.getText().trim()+"%");
                    ps.setString(19,"%"+TCari.getText().trim()+"%");
                    ps.setString(20,"%"+TCari.getText().trim()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("tgl_setor"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("jk"),
                        rs.getString("umur"),rs.getString("golongan_pasien").equalsIgnoreCase("mil ad")?"V":"",rs.getString("golongan_pasien").equalsIgnoreCase("pns ad")?"V":"",rs.getString("golongan_pasien").equalsIgnoreCase("kel ad")?"V":"",
                        rs.getString("golongan_pasien").equalsIgnoreCase("mil lain")?"V":"",rs.getString("golongan_pasien").equalsIgnoreCase("pns lain")?"V":"",rs.getString("golongan_pasien").equalsIgnoreCase("kel lain")?"V":"",
                        rs.getString("golongan_pasien").equalsIgnoreCase("jkn")?"V":"",rs.getString("golongan_pasien").equalsIgnoreCase("swasta")?"V":"",rs.getString("tgl_mrs"),rs.getString("tgl_krs"),
                        rs.getString("angka_lambat1"),rs.getString("ket_angka_lambat1"),
                        rs.getString("gc_isi").equalsIgnoreCase("1")?"V":"",rs.getString("gc_baca").equalsIgnoreCase("1")?"V":"",rs.getString("gc_tgl").equalsIgnoreCase("1")?"V":"",rs.getString("gc_stempel").equalsIgnoreCase("1")?"V":"",rs.getString("gc_koreksi").equalsIgnoreCase("1")?"V":"",
                        rs.getString("pam_isi").equalsIgnoreCase("1")?"V":"",rs.getString("pam_baca").equalsIgnoreCase("1")?"V":"",rs.getString("pam_tgl").equalsIgnoreCase("1")?"V":"",rs.getString("pam_stempel").equalsIgnoreCase("1")?"V":"",rs.getString("pam_koreksi").equalsIgnoreCase("1")?"V":"",
                        rs.getString("pak_isi").equalsIgnoreCase("1")?"V":"",rs.getString("pak_baca").equalsIgnoreCase("1")?"V":"",rs.getString("pak_tgl").equalsIgnoreCase("1")?"V":"",rs.getString("pak_stempel").equalsIgnoreCase("1")?"V":"",rs.getString("pak_koreksi").equalsIgnoreCase("1")?"V":"",
                        rs.getString("dp_isi").equalsIgnoreCase("1")?"V":"",rs.getString("dp_baca").equalsIgnoreCase("1")?"V":"",rs.getString("dp_tgl").equalsIgnoreCase("1")?"V":"",rs.getString("dp_stempel").equalsIgnoreCase("1")?"V":"",rs.getString("dp_koreksi").equalsIgnoreCase("1")?"V":"",
                        rs.getString("ro_isi").equalsIgnoreCase("1")?"V":"",rs.getString("ro_baca").equalsIgnoreCase("1")?"V":"",rs.getString("ro_tgl").equalsIgnoreCase("1")?"V":"",rs.getString("ro_stempel").equalsIgnoreCase("1")?"V":"",rs.getString("ro_koreksi").equalsIgnoreCase("1")?"V":"",
                        rs.getString("fe_isi").equalsIgnoreCase("1")?"V":"",rs.getString("fe_baca").equalsIgnoreCase("1")?"V":"",rs.getString("fe_tgl").equalsIgnoreCase("1")?"V":"",rs.getString("fe_stempel").equalsIgnoreCase("1")?"V":"",rs.getString("fe_koreksi").equalsIgnoreCase("1")?"V":"",
                        rs.getString("cppt_isi").equalsIgnoreCase("1")?"V":"",rs.getString("cppt_baca").equalsIgnoreCase("1")?"V":"",rs.getString("cppt_tgl").equalsIgnoreCase("1")?"V":"",rs.getString("cppt_stempel").equalsIgnoreCase("1")?"V":"",rs.getString("cppt_koreksi").equalsIgnoreCase("1")?"V":"",
                        rs.getString("ds_isi").equalsIgnoreCase("1")?"V":"",rs.getString("ds_baca").equalsIgnoreCase("1")?"V":"",rs.getString("ds_tgl").equalsIgnoreCase("1")?"V":"",rs.getString("ds_stempel").equalsIgnoreCase("1")?"V":"",rs.getString("ds_koreksi").equalsIgnoreCase("1")?"V":"",
                        rs.getString("kelengkapan").replaceAll("L","Lengkap").replaceAll("TL","Tidak Lengkap"),rs.getString("ket_kekurangan"),rs.getString("tgl_setor_kekurangan"),rs.getString("angka_lambat2"),rs.getString("ket_angka_lambat2"),rs.getString("komdik"),rs.getString("kd_ruangan"),rs.getString("nm_ruangan"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),
                        rs.getString("nm_diagnosa"),rs.getString("kd_diagnosa"),rs.getString("kesesuaian_diagnosa").equalsIgnoreCase("1")?"V":"",rs.getString("kesesuaian_diagnosa").equalsIgnoreCase("0")?"V":"",rs.getString("diagnosa_sekunder"),
                        rs.getString("nm_icd9"),rs.getString("kd_icd9"),rs.getString("kesesuaian_icd9").equalsIgnoreCase("1")?"V":"",rs.getString("kesesuaian_icd9").equalsIgnoreCase("0")?"V":"",
                        rs.getString("singkatan"),rs.getString("kesesuaian_singkatan").equalsIgnoreCase("1")?"V":"",rs.getString("kesesuaian_singkatan").equalsIgnoreCase("0")?"V":"",rs.getString("simbol"),rs.getString("kesesuaian_simbol").equalsIgnoreCase("1")?"V":"",rs.getString("kesesuaian_simbol").equalsIgnoreCase("0")?"V":"",
                        rs.getString("bukti_penunjang"),rs.getString("status_pulang"),rs.getString("tgl_setor_costing"),rs.getString("tgl_turun_costing"),rs.getString("angka_lambat3"),rs.getString("ket_angka_lambat3"),rs.getString("tgl_setor_filing"),rs.getString("no_sep"),rs.getString("no_rawat"),rs.getString("id")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }   
                if(ps!=null){
                    ps.close();
                }   
            }                
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount()); 
    }
    
    
    private void emptTeks(){
        TNoRM.setText("");
        TNoRW.setText("");
        TPasien.setText("");
        JK.setText("");
        Umur.setText("");
        NoSEP.setText("");
        tgl_mrs.setDate(new Date());
        tgl_krs.setDate(new Date());
        TanggalSetor.setDate(new Date());
        AngkaLambat1.setText("");
        KetAngkaLambat1.setText("");
        GolonganPasien.setSelectedIndex(1);
        ChkGC_isi.setSelected(false);
        ChkGC_baca.setSelected(false);
        ChkGC_tgl.setSelected(false);
        ChkGC_stempel.setSelected(false);
        ChkGC_koreksi.setSelected(false);
        ChkPAM_isi.setSelected(false);
        ChkPAM_baca.setSelected(false);
        ChkPAM_tgl.setSelected(false);
        ChkPAM_stempel.setSelected(false);
        ChkPAM_koreksi.setSelected(false);
        ChkPAK_isi.setSelected(false);
        ChkPAK_baca.setSelected(false);
        ChkPAK_tgl.setSelected(false);
        ChkPAK_stempel.setSelected(false);
        ChkPAK_koreksi.setSelected(false);
        ChkDP_isi.setSelected(false);
        ChkDP_baca.setSelected(false);
        ChkDP_tgl.setSelected(false);
        ChkDP_stempel.setSelected(false);
        ChkDP_koreksi.setSelected(false);
        ChkRO_isi.setSelected(false);
        ChkRO_baca.setSelected(false);
        ChkRO_tgl.setSelected(false);
        ChkRO_stempel.setSelected(false);
        ChkRO_koreksi.setSelected(false);
        ChkFE_isi.setSelected(false);
        ChkFE_baca.setSelected(false);
        ChkFE_tgl.setSelected(false);
        ChkFE_stempel.setSelected(false);
        ChkFE_koreksi.setSelected(false);
        ChkCPPT_isi.setSelected(false);
        ChkCPPT_baca.setSelected(false);
        ChkCPPT_tgl.setSelected(false);
        ChkCPPT_stempel.setSelected(false);
        ChkCPPT_koreksi.setSelected(false);
        ChkDS_isi.setSelected(false);
        ChkDS_baca.setSelected(false);
        ChkDS_tgl.setSelected(false);
        ChkDS_stempel.setSelected(false);
        ChkDS_koreksi.setSelected(false);
        Kelengkapan.setSelectedIndex(1);
        KetKekurangan.setText("");
        AngkaLambat2.setText("");
        KetAngkaLambat2.setText("");
        TanggalSetorKekurangan.setDate(new Date());
        KdDiagnosa.setText("");
        NmDiagnosa.setText("");
        ChkDiagnosa.setSelected(false);
        DiagnosaSekunder.setText("");
        KdTindakan.setText("");
        NmTindakan.setText("");
        ChkTindakan.setSelected(false);
        Singkatan.setText("");
        ChkSingkatan.setSelected(false);
        Simbol.setText("");
        ChkSimbol.setSelected(false);
        TglSetorCosting.setDate(new Date());
        TglTurunCosting.setDate(new Date());
        AngkaLambat3.setText("");
        KetAngkaLambat3.setText("");
        Komdik.setText("");
        KdRuangan.setText("");
        NmRuangan.setText("");
        KdDokter.setText("");
        NmDokter.setText("");
        BuktiPenunjang.setText("");
        StatusPulang.setSelectedIndex(1);
        TglSetorFiling.setDate(new Date());
    }
    
    public void setNoRm(String norm, String norwt, String jenis) {
        TNoRM.setText(norm);
        TNoRW.setText(norwt);
        TCari.setText(norwt);
        btnDiagnosa.setEnabled(false);
        btnTindakan.setEnabled(false);
        if(jenis=="RJ"){
            getDataRJ();
            status_lanjut="Ralan";
        }else{
            getDataRI();
            status_lanjut="Ranap";
        }
        hitungAngkaLambat1();
        hitungAngkaLambat2();
        hitungAngkaLambat3();
    }
      
    public void getDataRJ(){
        try {
            ps=koneksi.prepareStatement(
                    "SELECT r.no_rkm_medis, r.no_rawat, p.nm_pasien, p.jk, concat(r.umurdaftar,' ',r.sttsumur)as umur, po.nm_poli, d.nm_dokter, r.tgl_periksa \n" +
                    "FROM reg_periksa r\n" +
                    "INNER JOIN pasien p ON p.no_rkm_medis = r.no_rkm_medis\n" +
                    "INNER JOIN poliklinik po ON po.kd_poli = r.kd_poli\n" +
                    "INNER JOIN dokter d ON d.kd_dokter = r.kd_dokter\n" +
                    "WHERE r.status_lanjut = 'Ralan' AND r.no_rawat = '"+TNoRW.getText()+"'");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    TPasien.setText(rs.getString("nm_pasien"));
                    JK.setText(rs.getString("jk"));
                    Umur.setText(rs.getString("umur"));
                    tgl_mrs.setDate(Valid.SetTgl2(rs.getString("tgl_periksa")));
                    tgl_krs.setDate(Valid.SetTgl2(rs.getString("tgl_periksa")));
//                    StatusPulang.setEnabled(false);
                    StatusPulang.setSelectedItem("Lain-lain");
                    NmRuangan.setText(rs.getString("nm_poli"));
                    NmDokter.setText(rs.getString("nm_dokter"));
                    Sequel.cariIsi("select diagnosa_pasien.kd_penyakit from diagnosa_pasien where diagnosa_pasien.no_rawat=? ",KdDiagnosa,TNoRW.getText()); 
                    Sequel.cariIsi("select penyakit.nm_penyakit from penyakit where penyakit.kd_penyakit=? ",NmDiagnosa,KdDiagnosa.getText()); 
                    Sequel.cariIsi("select prosedur_pasien.kode from prosedur_pasien where prosedur_pasien.no_rawat=? ",KdTindakan,TNoRW.getText()); 
                    Sequel.cariIsi("select icd9.deskripsi_panjang from icd9 where icd9.kode=? ",NmTindakan,KdTindakan.getText());  
                    Sequel.cariIsi("select bridging_sep.no_sep from bridging_sep where bridging_sep.no_rawat=? ",NoSEP,TNoRW.getText()); 
                    TanggalSetor.setDate(Valid.SetTgl2(rs.getString("tgl_periksa")));
                    TglSetorCosting.setDate(Valid.SetTgl2(rs.getString("tgl_periksa")));
                    TglTurunCosting.setDate(Valid.SetTgl2(rs.getString("tgl_periksa")));
                    TanggalSetorKekurangan.setDate(Valid.SetTgl2(rs.getString("tgl_periksa")));
                    TglSetorFiling.setDate(Valid.SetTgl2(rs.getString("tgl_periksa")));
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }   
                if(ps!=null){
                    ps.close();
                }   
            }  
        } catch (SQLException ex) {
            Logger.getLogger(YanmedBerkasRM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getDataRI(){
        try {
            ps=koneksi.prepareStatement(
                    "SELECT r.no_rkm_medis, r.no_rawat, p.nm_pasien, p.jk, concat(r.umurdaftar,' ',r.sttsumur)as umur, ki.tgl_masuk, ki.tgl_keluar, ki.stts_pulang, b.nm_bangsal, d.nm_dokter\n" +
                    "FROM reg_periksa r\n" +
                    "INNER JOIN kamar_inap ki ON ki.no_rawat = r.no_rawat\n" +
                    "INNER JOIN pasien p ON p.no_rkm_medis = r.no_rkm_medis\n" +
                    "INNER JOIN kamar k ON k.kd_kamar = ki.kd_kamar\n" +
                    "INNER JOIN bangsal b ON b.kd_bangsal = k.kd_bangsal\n" +
                    "INNER JOIN dokter d ON d.kd_dokter = r.kd_dokter\n" +
                    "WHERE r.no_rawat = '"+TNoRW.getText()+"'");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    TPasien.setText(rs.getString("nm_pasien"));
                    JK.setText(rs.getString("jk"));
                    Umur.setText(rs.getString("umur"));
                    tgl_mrs.setDate(Valid.SetTgl2(rs.getString("tgl_masuk")));
                    tgl_krs.setDate(Valid.SetTgl2(rs.getString("tgl_keluar")));
                    StatusPulang.setSelectedItem(rs.getString("stts_pulang"));
                    NmRuangan.setText(rs.getString("nm_bangsal"));
                    NmDokter.setText(rs.getString("nm_dokter"));
                    Sequel.cariIsi("select diagnosa_pasien.kd_penyakit from diagnosa_pasien where diagnosa_pasien.no_rawat=? ",KdDiagnosa,TNoRW.getText()); 
                    Sequel.cariIsi("select penyakit.nm_penyakit from penyakit where penyakit.kd_penyakit=? ",NmDiagnosa,KdDiagnosa.getText()); 
                    Sequel.cariIsi("select prosedur_pasien.kode from prosedur_pasien where prosedur_pasien.no_rawat=? ",KdTindakan,TNoRW.getText()); 
                    Sequel.cariIsi("select icd9.deskripsi_panjang from icd9 where icd9.kode=? ",NmTindakan,KdTindakan.getText());  
                    Sequel.cariIsi("select bridging_sep.no_sep from bridging_sep where bridging_sep.no_rawat=? ",NoSEP,TNoRW.getText());  
                    TanggalSetor.setDate(Valid.SetTgl2(rs.getString("tgl_keluar")));
                    TglSetorCosting.setDate(Valid.SetTgl2(rs.getString("tgl_keluar")));
                    TglTurunCosting.setDate(Valid.SetTgl2(rs.getString("tgl_keluar")));
                    TanggalSetorKekurangan.setDate(Valid.SetTgl2(rs.getString("tgl_keluar")));
                    TglSetorFiling.setDate(Valid.SetTgl2(rs.getString("tgl_keluar")));
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }   
                if(ps!=null){
                    ps.close();
                }   
            }  
        } catch (SQLException ex) {
            Logger.getLogger(YanmedBerkasRM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getbpjs_sep());
        BtnHapus.setEnabled(akses.getbpjs_sep());
        BtnPrint.setEnabled(akses.getbpjs_sep());
        BtnEdit.setEnabled(akses.getbpjs_sep());     
    }
    
    private void getData() {
        if(tbDataLaporan.getSelectedRow()!= -1){
            Valid.SetTgl(TanggalSetor,tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),0).toString());
            TNoRM.setText(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),1).toString());
            TNoRW.setText(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),90).toString());
            TPasien.setText(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),2).toString());
            JK.setText(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),3).toString());
            Umur.setText(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),4).toString());
            GolonganPasien.setSelectedItem(Sequel.cariIsi("select golongan_pasien from laporan_berkasrm where no_rawat='"+TNoRW.getText()+"' and no_rkm_medis='"+TNoRM.getText()+"'"));
            Valid.SetTgl(tgl_mrs,tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),13).toString());
            Valid.SetTgl(tgl_krs,tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),14).toString());
            AngkaLambat1.setText(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),15).toString());
            KetAngkaLambat1.setText(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),16).toString());
            ChkGC_isi.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),17).equals("V"));
            ChkGC_baca.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),18).equals("V"));
            ChkGC_tgl.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),19).equals("V"));
            ChkGC_stempel.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),20).equals("V"));
            ChkGC_koreksi.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),21).equals("V"));
            ChkPAM_isi.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),22).equals("V"));
            ChkPAM_baca.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),23).equals("V"));
            ChkPAM_tgl.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),24).equals("V"));
            ChkPAM_stempel.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),25).equals("V"));
            ChkPAM_koreksi.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),26).equals("V"));
            ChkPAK_isi.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),27).equals("V"));
            ChkPAK_baca.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),28).equals("V"));
            ChkPAK_tgl.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),29).equals("V"));
            ChkPAK_stempel.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),30).equals("V"));
            ChkPAK_koreksi.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),31).equals("V"));
            ChkDP_isi.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),32).equals("V"));
            ChkDP_baca.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),33).equals("V"));
            ChkDP_tgl.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),34).equals("V"));
            ChkDP_stempel.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),35).equals("V"));
            ChkDP_koreksi.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),36).equals("V"));
            ChkRO_isi.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),37).equals("V"));
            ChkRO_baca.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),38).equals("V"));
            ChkRO_tgl.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),39).equals("V"));
            ChkRO_stempel.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),40).equals("V"));
            ChkRO_koreksi.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),41).equals("V"));
            ChkFE_isi.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),42).equals("V"));
            ChkFE_baca.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),43).equals("V"));
            ChkFE_tgl.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),44).equals("V"));
            ChkFE_stempel.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),45).equals("V"));
            ChkFE_koreksi.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),46).equals("V"));
            ChkCPPT_isi.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),47).equals("V"));
            ChkCPPT_baca.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),48).equals("V"));
            ChkCPPT_tgl.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),49).equals("V"));
            ChkCPPT_stempel.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),50).equals("V"));
            ChkCPPT_koreksi.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),51).equals("V"));
            ChkDS_isi.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),52).equals("V"));
            ChkDS_baca.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),53).equals("V"));
            ChkDS_tgl.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),54).equals("V"));
            ChkDS_stempel.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),55).equals("V"));
            ChkDS_koreksi.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),56).equals("V"));
            Kelengkapan.setSelectedItem(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),57).toString());
            KetKekurangan.setText(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),58).toString());
            Valid.SetTgl(TanggalSetorKekurangan,tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),59).toString());
            AngkaLambat2.setText(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),60).toString());
            KetAngkaLambat2.setText(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),61).toString());
            Komdik.setText(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),62).toString());
            KdRuangan.setText(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),63).toString());
            NmRuangan.setText(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),64).toString());
            KdDokter.setText(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),65).toString());
            NmDokter.setText(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),66).toString());
            NmDiagnosa.setText(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),67).toString());
            KdDiagnosa.setText(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),68).toString());
            ChkDiagnosa.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),69).equals("V"));
            DiagnosaSekunder.setText(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),71).toString());
            NmTindakan.setText(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),72).toString());
            KdTindakan.setText(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),73).toString());
            ChkTindakan.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),74).equals("V"));
            Singkatan.setText(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),76).toString());
            ChkSingkatan.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),77).equals("V"));
            Simbol.setText(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),79).toString());
            ChkSimbol.setSelected(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),80).equals("V"));
            BuktiPenunjang.setText(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),82).toString());
            StatusPulang.setSelectedItem(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),83).toString());
            Valid.SetTgl(TglSetorCosting,tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),84).toString());
            Valid.SetTgl(TglTurunCosting,tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),85).toString());
            AngkaLambat3.setText(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),86).toString());
            KetAngkaLambat3.setText(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),87).toString());
            Valid.SetTgl(TglSetorFiling,tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),88).toString());
            NoSEP.setText(tbDataLaporan.getValueAt(tbDataLaporan.getSelectedRow(),89).toString());
        }
    } 
    
    private void insertData(){
        try {
            if(Sequel.menyimpantf2("laporan_berkasrm","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No. Rawat",81,new String[]{
                "0",Valid.SetTgl(TanggalSetor.getSelectedItem()+""),TNoRM.getText(),TNoRW.getText(),TPasien.getText(),JK.getText(),Umur.getText(),GolonganPasien.getSelectedItem().toString(),
                Valid.SetTgl(tgl_mrs.getSelectedItem()+""),Valid.SetTgl(tgl_krs.getSelectedItem()+""),AngkaLambat1.getText(),KetAngkaLambat1.getText(),
                (ChkGC_isi.isSelected())?"1":"0",(ChkGC_baca.isSelected())?"1":"0",(ChkGC_tgl.isSelected())?"1":"0",(ChkGC_stempel.isSelected())?"1":"0",(ChkGC_koreksi.isSelected())?"1":"0",
                (ChkPAM_isi.isSelected())?"1":"0",(ChkPAM_baca.isSelected())?"1":"0",(ChkPAM_tgl.isSelected())?"1":"0",(ChkPAM_stempel.isSelected())?"1":"0",(ChkPAM_koreksi.isSelected())?"1":"0",
                (ChkPAK_isi.isSelected())?"1":"0",(ChkPAK_baca.isSelected())?"1":"0",(ChkPAK_tgl.isSelected())?"1":"0",(ChkPAK_stempel.isSelected())?"1":"0",(ChkPAK_koreksi.isSelected())?"1":"0",
                (ChkDP_isi.isSelected())?"1":"0",(ChkDP_baca.isSelected())?"1":"0",(ChkDP_tgl.isSelected())?"1":"0",(ChkDP_stempel.isSelected())?"1":"0",(ChkDP_koreksi.isSelected())?"1":"0",
                (ChkRO_isi.isSelected())?"1":"0",(ChkRO_baca.isSelected())?"1":"0",(ChkRO_tgl.isSelected())?"1":"0",(ChkRO_stempel.isSelected())?"1":"0",(ChkRO_koreksi.isSelected())?"1":"0",
                (ChkFE_isi.isSelected())?"1":"0",(ChkFE_baca.isSelected())?"1":"0",(ChkFE_tgl.isSelected())?"1":"0",(ChkFE_stempel.isSelected())?"1":"0",(ChkFE_koreksi.isSelected())?"1":"0",
                (ChkCPPT_isi.isSelected())?"1":"0",(ChkCPPT_baca.isSelected())?"1":"0",(ChkCPPT_tgl.isSelected())?"1":"0",(ChkCPPT_stempel.isSelected())?"1":"0",(ChkCPPT_koreksi.isSelected())?"1":"0",
                (ChkDS_isi.isSelected())?"1":"0",(ChkDS_baca.isSelected())?"1":"0",(ChkDS_tgl.isSelected())?"1":"0",(ChkDS_stempel.isSelected())?"1":"0",(ChkDS_koreksi.isSelected())?"1":"0",
                (Kelengkapan.getSelectedItem()=="Lengkap")?"L":"TL",KetKekurangan.getText(),Valid.SetTgl(TanggalSetorKekurangan.getSelectedItem()+""),AngkaLambat2.getText(),KetAngkaLambat2.getText(),
                Komdik.getText(),KdRuangan.getText(),NmRuangan.getText(),KdDokter.getText(),NmDokter.getText(),KdDiagnosa.getText(),NmDiagnosa.getText(),(ChkDiagnosa.isSelected())?"1":"0", 
                DiagnosaSekunder.getText(),KdTindakan.getText(),NmTindakan.getText(),(ChkTindakan.isSelected())?"1":"0",Singkatan.getText(),(ChkSingkatan.isSelected())?"1":"0",Simbol.getText(),(ChkSimbol.isSelected())?"1":"0",
                BuktiPenunjang.getText(),StatusPulang.getSelectedItem().toString(),Valid.SetTgl(TglSetorCosting.getSelectedItem()+""),Valid.SetTgl(TglTurunCosting.getSelectedItem()+""),
                AngkaLambat3.getText(),KetAngkaLambat3.getText(),Valid.SetTgl(TglSetorFiling.getSelectedItem()+""),NoSEP.getText()
            })==true){
                emptTeks();
            }
        }catch (Exception ex) {
            System.out.println("Notifikasi Bridging : "+ex);
            if(ex.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(null,"Koneksi ke server BPJS terputus...!");
            }
        }
    }
    
    private void deleteData(){
        Sequel.meghapus("laporan_berkasrm","no_rawat","no_sep",TNoRW.getText(), NoSEP.getText());
    }
    
    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
    
    public void hitungAngkaLambat1(){
        long datediff = getDifferenceDays(tgl_krs.getDate(), TanggalSetor.getDate());
        String keterangan = (datediff>2)?"TERLAMBAT":"TEPAT WAKTU";
        AngkaLambat1.setText(String.valueOf(datediff));
        KetAngkaLambat1.setText(keterangan);
    }
    
    public void hitungAngkaLambat2(){
        long datediff = getDifferenceDays(tgl_krs.getDate(), TanggalSetorKekurangan.getDate());
        String keterangan = (datediff>3)?"TERLAMBAT":"TEPAT WAKTU";
        AngkaLambat2.setText(String.valueOf(datediff));
        KetAngkaLambat2.setText(keterangan);
    }
    
    public void hitungAngkaLambat3(){
        long datediff = getDifferenceDays(TglSetorCosting.getDate(), TglTurunCosting.getDate());
        String keterangan = (datediff>2)?"TERLAMBAT":"TEPAT WAKTU";
        AngkaLambat3.setText(String.valueOf(datediff));
        KetAngkaLambat3.setText(keterangan);
    }
    
    private void exportSEPExcelActionPerformed(java.awt.event.ActionEvent evt) throws IOException { 
        File theDir = new File("C:\\DataBerkasRM\\");
        if (!theDir.exists()){
            theDir.mkdirs();
        }
        String excelFileName = "C:\\DataBerkasRM\\berkasRM_"+Valid.SetTgl(DTPCari1.getSelectedItem()+"").replace("-", "")+"-"+Valid.SetTgl(DTPCari2.getSelectedItem()+"").replace("-", "")+".xls";//name of excel file
        String sheetName = "Sheet1";//name of sheet
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetName) ;
        

        
        
        //Add first row
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell100 = row.createCell(0);
        cell100.setCellValue("Tgl. Setor");
        HSSFCell cell101 = row.createCell(1);
        cell101.setCellValue("No. RM");
        HSSFCell cell102 = row.createCell(2);
        cell102.setCellValue("Nama Pasien");
        HSSFCell cell103 = row.createCell(3);
        cell103.setCellValue("L/P");
        HSSFCell cell104 = row.createCell(4);
        cell104.setCellValue("Umur");
        HSSFCell cell105 = row.createCell(5);
        cell105.setCellValue("MIL AD");
        HSSFCell cell106 = row.createCell(6);
        cell106.setCellValue("PNS AD");
        HSSFCell cell107 = row.createCell(7);
        cell107.setCellValue("KEL AD");
        HSSFCell cell108 = row.createCell(8);
        cell108.setCellValue("MIL LAIN");
        HSSFCell cell109 = row.createCell(9);
        cell109.setCellValue("PNS LAIN");
        HSSFCell cell110 = row.createCell(10);
        cell110.setCellValue("KEL LAIN");
        HSSFCell cell111 = row.createCell(11);
        cell111.setCellValue("JKN");
        HSSFCell cell112 = row.createCell(12);
        cell112.setCellValue("Swasta");
        HSSFCell cell113 = row.createCell(13);
        cell113.setCellValue("MRS");
        HSSFCell cell114 = row.createCell(14);
        cell114.setCellValue("KRS");
        HSSFCell cell115 = row.createCell(15);
        cell115.setCellValue("Angka Lambat");
        HSSFCell cell116 = row.createCell(16);
        cell116.setCellValue("Keterangan");
        HSSFCell cell117 = row.createCell(17);
        cell117.setCellValue("GS Isi");
        HSSFCell cell118 = row.createCell(18);
        cell118.setCellValue("GS Baca");
        HSSFCell cell119 = row.createCell(19);
        cell119.setCellValue("GS Tgl & Jam");
        HSSFCell cell120 = row.createCell(20);
        cell120.setCellValue("GS Stempel & Nama");
        HSSFCell cell121 = row.createCell(21);
        cell121.setCellValue("GS Koreksi");
        HSSFCell cell122 = row.createCell(22);
        cell122.setCellValue("PAM ISI");
        HSSFCell cell123 = row.createCell(23);
        cell123.setCellValue("PAM Baca");
        HSSFCell cell124 = row.createCell(24);
        cell124.setCellValue("PAM Tgl & Stempel");
        HSSFCell cell125 = row.createCell(25);
        cell125.setCellValue("PAM Stempel & Nama");
        HSSFCell cell126 = row.createCell(26);
        cell126.setCellValue("PAM Koreksi");
        HSSFCell cell127 = row.createCell(27);
        cell127.setCellValue("PAK ISI");
        HSSFCell cell128 = row.createCell(28);
        cell128.setCellValue("PAK BACA");
        HSSFCell cell129 = row.createCell(29);
        cell129.setCellValue("PAK Tgl & Jam");
        HSSFCell cell130 = row.createCell(30);
        cell130.setCellValue("PAK Stempel & Nama");
        HSSFCell cell131 = row.createCell(31);
        cell131.setCellValue("PAK Koreksi");
        HSSFCell cell132 = row.createCell(32);
        cell132.setCellValue("DP Isi");
        HSSFCell cell133 = row.createCell(33);
        cell133.setCellValue("DP Baca");
        HSSFCell cell134 = row.createCell(34);
        cell134.setCellValue("DP Tgl & Jam");
        HSSFCell cell135 = row.createCell(35);
        cell135.setCellValue("DP Stempel & Nama");
        HSSFCell cell136 = row.createCell(36);
        cell136.setCellValue("DP Koreksi");
        HSSFCell cell137 = row.createCell(37);
        cell137.setCellValue("RO Isi");
        HSSFCell cell138 = row.createCell(38);
        cell138.setCellValue("RO Baca");
        HSSFCell cell139 = row.createCell(39);
        cell139.setCellValue("RO Tgl & Jam ");
        HSSFCell cell140 = row.createCell(40);
        cell140.setCellValue("RO Stempel & Nama");
        HSSFCell cell141 = row.createCell(41);
        cell141.setCellValue("RO Koreksi");
        HSSFCell cell142 = row.createCell(42);
        cell142.setCellValue("FE Isi");
        HSSFCell cell143 = row.createCell(43);
        cell143.setCellValue("FE Baca");
        HSSFCell cell144 = row.createCell(44);
        cell144.setCellValue("FE Tgl & Jam");
        HSSFCell cell145 = row.createCell(45);
        cell145.setCellValue("FE Stempel & Nama");
        HSSFCell cell146 = row.createCell(46);
        cell146.setCellValue("FE Koreksi");
        HSSFCell cell147 = row.createCell(47);
        cell147.setCellValue("CPPT Isi");
        HSSFCell cell148 = row.createCell(48);
        cell148.setCellValue("CPPT Baca");
        HSSFCell cell149 = row.createCell(49);
        cell149.setCellValue("CPPT Tgl & Jam");
        HSSFCell cell150 = row.createCell(50);
        cell150.setCellValue("CPPT Stempel & Nama");
        HSSFCell cell151 = row.createCell(51);
        cell151.setCellValue("CPPT Koreksi");
        HSSFCell cell152 = row.createCell(52);
        cell152.setCellValue("DS Isi");
        HSSFCell cell153 = row.createCell(53);
        cell153.setCellValue("DS Baca");
        HSSFCell cell154 = row.createCell(54);
        cell154.setCellValue("DS Tgl & Jam");
        HSSFCell cell155 = row.createCell(55);
        cell155.setCellValue("DS Stempel & Nama");
        HSSFCell cell156 = row.createCell(56);
        cell156.setCellValue("DS Koreksi");
        HSSFCell cell157 = row.createCell(57);
        cell157.setCellValue("TL");
        HSSFCell cell158 = row.createCell(58);
        cell158.setCellValue("Ket. Kekurangan");
        HSSFCell cell159 = row.createCell(59);
        cell159.setCellValue("Tgl Setor Kekurangan");
        HSSFCell cell160 = row.createCell(60);
        cell160.setCellValue("Angka Lambat 2");
        HSSFCell cell161 = row.createCell(61);
        cell161.setCellValue("Keterangan");
        HSSFCell cell162 = row.createCell(62);
        cell162.setCellValue("Dari Komdik");
        HSSFCell cell163 = row.createCell(63);
        cell163.setCellValue("Kode");
        HSSFCell cell164 = row.createCell(64);
        cell164.setCellValue("Ruangan");
        HSSFCell cell165 = row.createCell(65);
        cell165.setCellValue("Kode");
        HSSFCell cell166 = row.createCell(66);
        cell166.setCellValue("Nama Dokter");
        HSSFCell cell167 = row.createCell(67);
        cell167.setCellValue("Dx Utama");
        HSSFCell cell168 = row.createCell(68);
        cell168.setCellValue("ICD 10 Kode");
        HSSFCell cell169 = row.createCell(69);
        cell169.setCellValue("ICD 10 Sesuai");
        HSSFCell cell170 = row.createCell(70);
        cell170.setCellValue("ICD 10 Tidak Sesuai");
        HSSFCell cell171 = row.createCell(71);
        cell171.setCellValue("Dx Sekunder");
        HSSFCell cell172 = row.createCell(72);
        cell172.setCellValue("Tx Operasi");
        HSSFCell cell173 = row.createCell(73);
        cell173.setCellValue("ICD 9 Kode");
        HSSFCell cell174 = row.createCell(74);
        cell174.setCellValue("ICD 9 Sesuai");
        HSSFCell cell175 = row.createCell(75);
        cell175.setCellValue("ICD 9 Tidak Sesuai");
        HSSFCell cell176 = row.createCell(76);
        cell176.setCellValue("Singkatan Kode");
        HSSFCell cell177 = row.createCell(77);
        cell177.setCellValue("Singkatan Sesuai");
        HSSFCell cell178 = row.createCell(78);
        cell178.setCellValue("Singkatan Tidak Sesuai");
        HSSFCell cell179 = row.createCell(79);
        cell179.setCellValue("Simbol Kode");
        HSSFCell cell180 = row.createCell(70);
        cell180.setCellValue("Simbol Sesuai");
        HSSFCell cell181 = row.createCell(81);
        cell181.setCellValue("Simbol Tidak Sesuai");
        HSSFCell cell182 = row.createCell(82);
        cell182.setCellValue("Bukti Penunjang");
        HSSFCell cell183 = row.createCell(83);
        cell183.setCellValue("Ket. Pulang");
        HSSFCell cell184 = row.createCell(84);
        cell184.setCellValue("Tgl Setor Costing");
        HSSFCell cell185 = row.createCell(85);
        cell185.setCellValue("Tgl Turun dari Costing");
        HSSFCell cell186 = row.createCell(86);
        cell186.setCellValue("Angka Lambat 3");
        HSSFCell cell187 = row.createCell(87);
        cell187.setCellValue("Keterangan");
        HSSFCell cell188 = row.createCell(88);
        cell188.setCellValue("Tgl Setor Ke Filing");
        HSSFCell cell189 = row.createCell(89);
        cell189.setCellValue("No. SEP");
        HSSFCell cell190 = row.createCell(90);
        cell190.setCellValue("No Rawat");
        
        int rowNum = 0;
        int z=tabMode.getRowCount();
        for(i=0;i<z;i++){
            rowNum = i+1;
            HSSFRow row1 = sheet.createRow(rowNum);
            HSSFCell cell200 = row1.createCell(0);
            cell200.setCellValue(tabMode.getValueAt(i,0).toString());
            HSSFCell cell201 = row1.createCell(1);
            cell201.setCellValue(tabMode.getValueAt(i,1).toString());
            HSSFCell cell202 = row1.createCell(2);
            cell202.setCellValue(tabMode.getValueAt(i,2).toString());
            HSSFCell cell203 = row1.createCell(3);
            cell203.setCellValue(tabMode.getValueAt(i,3).toString());
            HSSFCell cell204 = row1.createCell(4);
            cell204.setCellValue(tabMode.getValueAt(i,4).toString());
            HSSFCell cell205 = row1.createCell(5);
            cell205.setCellValue(tabMode.getValueAt(i,5).toString());
            HSSFCell cell206 = row1.createCell(6);
            cell206.setCellValue(tabMode.getValueAt(i,6).toString());
            HSSFCell cell207 = row1.createCell(7);
            cell207.setCellValue(tabMode.getValueAt(i,7).toString());
            HSSFCell cell208 = row1.createCell(8);
            cell208.setCellValue(tabMode.getValueAt(i,8).toString());
            HSSFCell cell209 = row1.createCell(9);
            cell209.setCellValue(tabMode.getValueAt(i,9).toString());
            HSSFCell cell210 = row1.createCell(10);
            cell210.setCellValue(tabMode.getValueAt(i,10).toString());
            HSSFCell cell211 = row1.createCell(11);
            cell211.setCellValue(tabMode.getValueAt(i,11).toString());
            HSSFCell cell212 = row1.createCell(12);
            cell212.setCellValue(tabMode.getValueAt(i,12).toString());
            HSSFCell cell213 = row1.createCell(13);
            cell213.setCellValue(tabMode.getValueAt(i,13).toString());
            HSSFCell cell214 = row1.createCell(14);
            cell214.setCellValue(tabMode.getValueAt(i,14).toString());
            HSSFCell cell215 = row1.createCell(15);
            cell215.setCellValue(tabMode.getValueAt(i,15).toString());
            HSSFCell cell216 = row1.createCell(16);
            cell216.setCellValue(tabMode.getValueAt(i,16).toString());
            HSSFCell cell217 = row1.createCell(17);
            cell217.setCellValue(tabMode.getValueAt(i,17).toString());
            HSSFCell cell218 = row1.createCell(18);
            cell218.setCellValue(tabMode.getValueAt(i,18).toString());
            HSSFCell cell219 = row1.createCell(19);
            cell219.setCellValue(tabMode.getValueAt(i,19).toString());
            HSSFCell cell220 = row1.createCell(20);
            cell220.setCellValue(tabMode.getValueAt(i,20).toString());
            HSSFCell cell221 = row1.createCell(21);
            cell221.setCellValue(tabMode.getValueAt(i,21).toString());
            HSSFCell cell222 = row1.createCell(22);
            cell222.setCellValue(tabMode.getValueAt(i,22).toString());
            HSSFCell cell223 = row1.createCell(23);
            cell223.setCellValue(tabMode.getValueAt(i,23).toString());
            HSSFCell cell224 = row1.createCell(24);
            cell224.setCellValue(tabMode.getValueAt(i,24).toString());
            HSSFCell cell225 = row1.createCell(25);
            cell225.setCellValue(tabMode.getValueAt(i,25).toString());
            HSSFCell cell226 = row1.createCell(26);
            cell226.setCellValue(tabMode.getValueAt(i,26).toString());
            HSSFCell cell227 = row1.createCell(27);
            cell227.setCellValue(tabMode.getValueAt(i,27).toString());
            HSSFCell cell228 = row1.createCell(28);
            cell228.setCellValue(tabMode.getValueAt(i,28).toString());
            HSSFCell cell229 = row1.createCell(29);
            cell229.setCellValue(tabMode.getValueAt(i,29).toString());
            HSSFCell cell230 = row1.createCell(30);
            cell230.setCellValue(tabMode.getValueAt(i,30).toString());
            HSSFCell cell231 = row1.createCell(31);
            cell231.setCellValue(tabMode.getValueAt(i,31).toString());
            HSSFCell cell232 = row1.createCell(32);
            cell232.setCellValue(tabMode.getValueAt(i,32).toString());
            HSSFCell cell233 = row1.createCell(33);
            cell233.setCellValue(tabMode.getValueAt(i,33).toString());
            HSSFCell cell234 = row1.createCell(34);
            cell234.setCellValue(tabMode.getValueAt(i,34).toString());
            HSSFCell cell235 = row1.createCell(35);
            cell235.setCellValue(tabMode.getValueAt(i,35).toString());
            HSSFCell cell236 = row1.createCell(36);
            cell236.setCellValue(tabMode.getValueAt(i,36).toString());
            HSSFCell cell237 = row1.createCell(37);
            cell237.setCellValue(tabMode.getValueAt(i,37).toString());
            HSSFCell cell238 = row1.createCell(38);
            cell238.setCellValue(tabMode.getValueAt(i,38).toString());
            HSSFCell cell239 = row1.createCell(39);
            cell239.setCellValue(tabMode.getValueAt(i,39).toString());
            HSSFCell cell240 = row1.createCell(40);
            cell240.setCellValue(tabMode.getValueAt(i,40).toString());
            HSSFCell cell241 = row1.createCell(41);
            cell241.setCellValue(tabMode.getValueAt(i,41).toString());
            HSSFCell cell242 = row1.createCell(42);
            cell242.setCellValue(tabMode.getValueAt(i,42).toString());
            HSSFCell cell243 = row1.createCell(43);
            cell243.setCellValue(tabMode.getValueAt(i,43).toString());
            HSSFCell cell244 = row1.createCell(44);
            cell244.setCellValue(tabMode.getValueAt(i,44).toString());
            HSSFCell cell245 = row1.createCell(45);
            cell245.setCellValue(tabMode.getValueAt(i,45).toString());
            HSSFCell cell246 = row1.createCell(46);
            cell246.setCellValue(tabMode.getValueAt(i,46).toString());
            HSSFCell cell247 = row1.createCell(47);
            cell247.setCellValue(tabMode.getValueAt(i,47).toString());
            HSSFCell cell248 = row1.createCell(48);
            cell248.setCellValue(tabMode.getValueAt(i,48).toString());
            HSSFCell cell249 = row1.createCell(49);
            cell249.setCellValue(tabMode.getValueAt(i,49).toString());
            HSSFCell cell250 = row1.createCell(50);
            cell250.setCellValue(tabMode.getValueAt(i,50).toString());
            HSSFCell cell251 = row1.createCell(51);
            cell251.setCellValue(tabMode.getValueAt(i,51).toString());
            HSSFCell cell252 = row1.createCell(52);
            cell252.setCellValue(tabMode.getValueAt(i,52).toString());
            HSSFCell cell253 = row1.createCell(53);
            cell253.setCellValue(tabMode.getValueAt(i,53).toString());
            HSSFCell cell254 = row1.createCell(54);
            cell254.setCellValue(tabMode.getValueAt(i,54).toString());
            HSSFCell cell255 = row1.createCell(55);
            cell255.setCellValue(tabMode.getValueAt(i,55).toString());
            HSSFCell cell256 = row1.createCell(56);
            cell256.setCellValue(tabMode.getValueAt(i,56).toString());
            HSSFCell cell257 = row1.createCell(57);
            cell257.setCellValue(tabMode.getValueAt(i,57).toString());
            HSSFCell cell258 = row1.createCell(58);
            cell258.setCellValue(tabMode.getValueAt(i,58).toString());
            HSSFCell cell259 = row1.createCell(59);
            cell259.setCellValue(tabMode.getValueAt(i,59).toString());
            HSSFCell cell260 = row1.createCell(60);
            cell260.setCellValue(tabMode.getValueAt(i,60).toString());
            HSSFCell cell261 = row1.createCell(61);
            cell261.setCellValue(tabMode.getValueAt(i,61).toString());
            HSSFCell cell262 = row1.createCell(62);
            cell262.setCellValue(tabMode.getValueAt(i,62).toString());
            HSSFCell cell263 = row1.createCell(63);
            cell263.setCellValue(tabMode.getValueAt(i,63).toString());
            HSSFCell cell264 = row1.createCell(64);
            cell264.setCellValue(tabMode.getValueAt(i,64).toString());
            HSSFCell cell265 = row1.createCell(65);
            cell265.setCellValue(tabMode.getValueAt(i,65).toString());
            HSSFCell cell266 = row1.createCell(66);
            cell266.setCellValue(tabMode.getValueAt(i,66).toString());
            HSSFCell cell267 = row1.createCell(67);
            cell267.setCellValue(tabMode.getValueAt(i,67).toString());
            HSSFCell cell268 = row1.createCell(68);
            cell268.setCellValue(tabMode.getValueAt(i,68).toString());
            HSSFCell cell269 = row1.createCell(69);
            cell269.setCellValue(tabMode.getValueAt(i,69).toString());
            HSSFCell cell270 = row1.createCell(70);
            cell270.setCellValue(tabMode.getValueAt(i,70).toString());
            HSSFCell cell271 = row1.createCell(71);
            cell271.setCellValue(tabMode.getValueAt(i,71).toString());
            HSSFCell cell272 = row1.createCell(72);
            cell272.setCellValue(tabMode.getValueAt(i,72).toString());
            HSSFCell cell273 = row1.createCell(73);
            cell273.setCellValue(tabMode.getValueAt(i,73).toString());
            HSSFCell cell274 = row1.createCell(74);
            cell274.setCellValue(tabMode.getValueAt(i,74).toString());
            HSSFCell cell275 = row1.createCell(75);
            cell275.setCellValue(tabMode.getValueAt(i,75).toString());
            HSSFCell cell276 = row1.createCell(76);
            cell276.setCellValue(tabMode.getValueAt(i,76).toString());
            HSSFCell cell277 = row1.createCell(77);
            cell277.setCellValue(tabMode.getValueAt(i,77).toString());
            HSSFCell cell278 = row1.createCell(78);
            cell278.setCellValue(tabMode.getValueAt(i,78).toString());
            HSSFCell cell279 = row1.createCell(79);
            cell279.setCellValue(tabMode.getValueAt(i,79).toString());
            HSSFCell cell280 = row1.createCell(70);
            cell280.setCellValue(tabMode.getValueAt(i,80).toString());
            HSSFCell cell281 = row1.createCell(81);
            cell281.setCellValue(tabMode.getValueAt(i,81).toString());
            HSSFCell cell282 = row1.createCell(82);
            cell282.setCellValue(tabMode.getValueAt(i,82).toString());
            HSSFCell cell283 = row1.createCell(83);
            cell283.setCellValue(tabMode.getValueAt(i,83).toString());
            HSSFCell cell284 = row1.createCell(84);
            cell284.setCellValue(tabMode.getValueAt(i,84).toString());
            HSSFCell cell285 = row1.createCell(85);
            cell285.setCellValue(tabMode.getValueAt(i,85).toString());
            HSSFCell cell286 = row1.createCell(86);
            cell286.setCellValue(tabMode.getValueAt(i,86).toString());
            HSSFCell cell287 = row1.createCell(87);
            cell287.setCellValue(tabMode.getValueAt(i,87).toString());
            HSSFCell cell288 = row1.createCell(88);
            cell288.setCellValue(tabMode.getValueAt(i,88).toString());
            HSSFCell cell289 = row1.createCell(89);
            cell289.setCellValue(tabMode.getValueAt(i,89).toString());
            HSSFCell cell290 = row1.createCell(90);
            cell290.setCellValue(tabMode.getValueAt(i,90).toString());
            rowNum =+ 1;
                                                              
        }
        
        FileOutputStream fileOut = new FileOutputStream(excelFileName);

        //write this workbook to an Outputstream.
        wb.write(fileOut);
        fileOut.flush();
        fileOut.close();
        System.out.println("Data berhasil diexport di "+excelFileName);
        JOptionPane.showMessageDialog(null,"Data berhasil diexport di "+excelFileName);
    }
}
