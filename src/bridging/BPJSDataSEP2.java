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

package bridging;

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
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import simrskhanza.DlgKamarInap;
import rekammedis.RMRiwayatPerawatan;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
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

/**
 *
 * @author perpustakaan
 */
public final class BPJSDataSEP2 extends javax.swing.JDialog {
    private DefaultTableModel tabMode,tabModeRI,tabModeInternal;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0,pilihan=1,reply=0,tab=0;
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
    private ApiBPJS api=new ApiBPJS();
//    private BPJSCekReferensiFaskes faskes=new BPJSCekReferensiFaskes(null,false);
//    private BPJSCekReferensiPenyakit penyakit=new BPJSCekReferensiPenyakit(null,false);
//    private BPJSCekReferensiPoli poli=new BPJSCekReferensiPoli(null,false);
//    private BPJSCekNoKartu cekViaBPJSKartu=new BPJSCekNoKartu();
//    private BPJSCekReferensiDokterDPJP dokter=new BPJSCekReferensiDokterDPJP(null,false);
//    private BPJSSuratKontrol skdp=new BPJSSuratKontrol(null,false);
//    private BPJSSPRI skdp2=new BPJSSPRI(null,false);
//    private BPJSCekReferensiPropinsi propinsi=new BPJSCekReferensiPropinsi(null,false);
//    private BPJSCekReferensiKabupaten kabupaten=new BPJSCekReferensiKabupaten(null,false);
//    private BPJSCekReferensiKecamatan kecamatan=new BPJSCekReferensiKecamatan(null,false);
    private String prb="",no_peserta="",link="", requestJson,URL="",query="",utc="",user="",kddokter="",tglkkl="0000-00-00",penunjang="";
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private BPJSCekHistoriPelayanan historiPelayanan=new BPJSCekHistoriPelayanan(null,false);
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public BPJSDataSEP2(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        
        tabMode=new DefaultTableModel(null,new Object[]{
                "No.SEP","No.Rawat","No.RM","Nama Pasien","Tgl.SEP","Tgl.Rujukan","No.Rujukan","Kode PPK Rujukan","Nama PPK Rujukan","Kode PPK Pelayanan", 
                "Nama PPK Pelayanan","Jenis","Catatan", "Kode Diagnosa","Nama Diagnosa", "Kode Poli", "Nama Poli", "Kelas Rawat","Naik Kelas",
                "Pembiayaan","P.J.Naik Kelas","Laka Lantas","User Input","Tgl.Lahir","Peserta","J.K","No.Kartu","Tanggal Pulang","Asal Rujukan","Eksekutif",
                "COB","No.Telp","Katarak","Tanggal KKL","Keterangan KKL","Suplesi","No.SEP Suplesi","Kd Prop","Propinsi","Kd Kab","Kabupaten","Kd Kec",
                "Kecamatan","No.SKDP","Kd DPJP","DPJP","Tujuan Kunjungan","Flag Prosedur","Penunjang","Asesmen Pelayanan","Kd DPJP Layan","DPJP Layanan"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDataSEP.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbDataSEP.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDataSEP.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 52; i++) {
            TableColumn column = tbDataSEP.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(125);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(67);
            }else if(i==5){
                column.setPreferredWidth(67);
            }else if(i==6){
                column.setPreferredWidth(110);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==8){
                column.setPreferredWidth(140);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setPreferredWidth(140);
            }else if(i==11){
                column.setPreferredWidth(60);
            }else if(i==12){
                column.setPreferredWidth(140);
            }else if(i==13){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==14){
                column.setPreferredWidth(180);
            }else if(i==15){
                column.setPreferredWidth(60);
            }else if(i==16){
                column.setPreferredWidth(140);
            }else if(i==17){
                column.setPreferredWidth(70);
            }else if(i==18){
                column.setPreferredWidth(70);
            }else if(i==19){
                column.setPreferredWidth(100);
            }else if(i==20){
                column.setPreferredWidth(140);
            }else if(i==21){
                column.setPreferredWidth(68);
            }else if(i==22){
                column.setPreferredWidth(90);
            }else if(i==23){
                column.setPreferredWidth(67);
            }else if(i==24){
                column.setPreferredWidth(100);
            }else if(i==25){
                column.setPreferredWidth(30);
            }else if(i==26){
                column.setPreferredWidth(90);
            }else if(i==27){
                column.setPreferredWidth(115);
            }else if(i==28){
                column.setPreferredWidth(75);
            }else if(i==29){
                column.setPreferredWidth(53);
            }else if(i==30){
                column.setPreferredWidth(53);
            }else if(i==31){
                column.setPreferredWidth(100);
            }else if(i==32){
                column.setPreferredWidth(53);
            }else if(i==33){
                column.setPreferredWidth(67);
            }else if(i==34){
                column.setPreferredWidth(140);
            }else if(i==35){
                column.setPreferredWidth(53);
            }else if(i==36){
                column.setPreferredWidth(125);
            }else if(i==37){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==38){
                column.setPreferredWidth(110);
            }else if(i==39){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==40){
                column.setPreferredWidth(110);
            }else if(i==41){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==42){
                column.setPreferredWidth(110);
            }else if(i==43){
                column.setPreferredWidth(110);
            }else if(i==44){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==45){
                column.setPreferredWidth(160);
            }else if(i==46){
                column.setPreferredWidth(100);
            }else if(i==47){
                column.setPreferredWidth(170);
            }else if(i==48){
                column.setPreferredWidth(130);
            }else if(i==49){
                column.setPreferredWidth(170);
            }else if(i==50){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==51){
                column.setPreferredWidth(170);
            }
        }
        
        tabModeRI=new DefaultTableModel(null,new Object[]{
                "No.SEP","No.Rawat","No.RM","Nama Pasien","Tgl.SEP","Tgl.Rujukan","No.Rujukan","Kode PPK Rujukan","Nama PPK Rujukan","Kode PPK Pelayanan", 
                "Nama PPK Pelayanan","Jenis","Catatan", "Kode Diagnosa","Nama Diagnosa", "Kode Poli", "Nama Poli", "Kelas Rawat","Naik Kelas",
                "Pembiayaan","P.J.Naik Kelas","Laka Lantas","User Input","Tgl.Lahir","Peserta","J.K","No.Kartu","Tanggal Pulang","Asal Rujukan","Eksekutif",
                "COB","No.Telp","Katarak","Tanggal KKL","Keterangan KKL","Suplesi","No.SEP Suplesi","Kd Prop","Propinsi","Kd Kab","Kabupaten","Kd Kec",
                "Kecamatan","No.SKDP","Kd DPJP","DPJP","Tujuan Kunjungan","Flag Prosedur","Penunjang","Asesmen Pelayanan","Kd DPJP Layan","DPJP Layanan"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDataSEP2.setModel(tabModeRI);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbDataSEP2.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDataSEP2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 52; i++) {
            TableColumn column = tbDataSEP2.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(125);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(67);
            }else if(i==5){
                column.setPreferredWidth(67);
            }else if(i==6){
                column.setPreferredWidth(110);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==8){
                column.setPreferredWidth(140);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setPreferredWidth(140);
            }else if(i==11){
                column.setPreferredWidth(60);
            }else if(i==12){
                column.setPreferredWidth(140);
            }else if(i==13){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==14){
                column.setPreferredWidth(180);
            }else if(i==15){
                column.setPreferredWidth(60);
            }else if(i==16){
                column.setPreferredWidth(140);
            }else if(i==17){
                column.setPreferredWidth(70);
            }else if(i==18){
                column.setPreferredWidth(70);
            }else if(i==19){
                column.setPreferredWidth(100);
            }else if(i==20){
                column.setPreferredWidth(140);
            }else if(i==21){
                column.setPreferredWidth(68);
            }else if(i==22){
                column.setPreferredWidth(90);
            }else if(i==23){
                column.setPreferredWidth(67);
            }else if(i==24){
                column.setPreferredWidth(100);
            }else if(i==25){
                column.setPreferredWidth(30);
            }else if(i==26){
                column.setPreferredWidth(90);
            }else if(i==27){
                column.setPreferredWidth(115);
            }else if(i==28){
                column.setPreferredWidth(75);
            }else if(i==29){
                column.setPreferredWidth(53);
            }else if(i==30){
                column.setPreferredWidth(53);
            }else if(i==31){
                column.setPreferredWidth(100);
            }else if(i==32){
                column.setPreferredWidth(53);
            }else if(i==33){
                column.setPreferredWidth(67);
            }else if(i==34){
                column.setPreferredWidth(140);
            }else if(i==35){
                column.setPreferredWidth(53);
            }else if(i==36){
                column.setPreferredWidth(125);
            }else if(i==37){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==38){
                column.setPreferredWidth(110);
            }else if(i==39){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==40){
                column.setPreferredWidth(110);
            }else if(i==41){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==42){
                column.setPreferredWidth(110);
            }else if(i==43){
                column.setPreferredWidth(110);
            }else if(i==44){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==45){
                column.setPreferredWidth(160);
            }else if(i==46){
                column.setPreferredWidth(100);
            }else if(i==47){
                column.setPreferredWidth(170);
            }else if(i==48){
                column.setPreferredWidth(130);
            }else if(i==49){
                column.setPreferredWidth(170);
            }else if(i==50){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==51){
                column.setPreferredWidth(170);
            }
        }
        tbDataSEP.setDefaultRenderer(Object.class, new WarnaTable());
        tbDataSEP2.setDefaultRenderer(Object.class, new WarnaTable());
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        
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

        Popup = new javax.swing.JPopupMenu();
        ppSEP = new javax.swing.JMenuItem();
        ppSEP1 = new javax.swing.JMenuItem();
        ppSEP2 = new javax.swing.JMenuItem();
        ppSEP3 = new javax.swing.JMenuItem();
        ppExportDataExcel = new javax.swing.JMenuItem();
        NoBalasan = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame4 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbDataSEP = new widget.Table();
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
        internalFrame6 = new widget.InternalFrame();
        Scroll2 = new widget.ScrollPane();
        tbDataSEP2 = new widget.Table();
        panelGlass11 = new widget.panelisi();
        jLabel23 = new widget.Label();
        DTPCari5 = new widget.Tanggal();
        jLabel24 = new widget.Label();
        DTPCari6 = new widget.Tanggal();
        jLabel10 = new widget.Label();
        TCari2 = new widget.TextBox();
        BtnCari2 = new widget.Button();
        jLabel11 = new widget.Label();
        LCount2 = new widget.Label();
        panelGlass8 = new widget.panelisi();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();

        Popup.setName("Popup"); // NOI18N

        ppSEP.setBackground(new java.awt.Color(255, 255, 254));
        ppSEP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSEP.setForeground(new java.awt.Color(50, 50, 50));
        ppSEP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSEP.setText("Print SEP Model 1");
        ppSEP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSEP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSEP.setName("ppSEP"); // NOI18N
        ppSEP.setPreferredSize(new java.awt.Dimension(300, 25));
        ppSEP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSEPBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSEP);

        ppSEP1.setBackground(new java.awt.Color(255, 255, 254));
        ppSEP1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSEP1.setForeground(new java.awt.Color(50, 50, 50));
        ppSEP1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSEP1.setText("Print SEP + Resume Medis");
        ppSEP1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSEP1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSEP1.setName("ppSEP1"); // NOI18N
        ppSEP1.setPreferredSize(new java.awt.Dimension(300, 25));
        ppSEP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSEP1BtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSEP1);

        ppSEP2.setBackground(new java.awt.Color(255, 255, 254));
        ppSEP2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSEP2.setForeground(new java.awt.Color(50, 50, 50));
        ppSEP2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSEP2.setText("Print SEP Model 3");
        ppSEP2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSEP2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSEP2.setName("ppSEP2"); // NOI18N
        ppSEP2.setPreferredSize(new java.awt.Dimension(300, 25));
        ppSEP2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSEP2BtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSEP2);

        ppSEP3.setBackground(new java.awt.Color(255, 255, 254));
        ppSEP3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSEP3.setForeground(new java.awt.Color(50, 50, 50));
        ppSEP3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSEP3.setText("Print SEP + Case Mix INACBG");
        ppSEP3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSEP3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSEP3.setName("ppSEP3"); // NOI18N
        ppSEP3.setPreferredSize(new java.awt.Dimension(300, 25));
        ppSEP3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSEP3BtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSEP3);

        ppExportDataExcel.setBackground(new java.awt.Color(255, 255, 254));
        ppExportDataExcel.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppExportDataExcel.setForeground(new java.awt.Color(50, 50, 50));
        ppExportDataExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppExportDataExcel.setText("Export Data Excel");
        ppExportDataExcel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppExportDataExcel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppExportDataExcel.setName("ppExportDataExcel"); // NOI18N
        ppExportDataExcel.setPreferredSize(new java.awt.Dimension(300, 25));
        ppExportDataExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppExportDataExcel(evt);
            }
        });
        Popup.add(ppExportDataExcel);

        NoBalasan.setHighlighter(null);
        NoBalasan.setName("NoBalasan"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Bridging SEP BPJS ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setComponentPopupMenu(Popup);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbDataSEP.setAutoCreateRowSorter(true);
        tbDataSEP.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDataSEP.setComponentPopupMenu(Popup);
        tbDataSEP.setName("tbDataSEP"); // NOI18N
        tbDataSEP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataSEPMouseClicked(evt);
            }
        });
        tbDataSEP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDataSEPKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbDataSEP);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-02-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-02-2023" }));
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

        TabRawat.addTab("Data SEP Rawat Jalan", internalFrame4);

        internalFrame6.setBorder(null);
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll2.setComponentPopupMenu(Popup);
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbDataSEP2.setAutoCreateRowSorter(true);
        tbDataSEP2.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDataSEP2.setComponentPopupMenu(Popup);
        tbDataSEP2.setName("tbDataSEP2"); // NOI18N
        tbDataSEP2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataSEP2MouseClicked(evt);
            }
        });
        tbDataSEP2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDataSEP2KeyPressed(evt);
            }
        });
        Scroll2.setViewportView(tbDataSEP2);

        internalFrame6.add(Scroll2, java.awt.BorderLayout.CENTER);

        panelGlass11.setBorder(null);
        panelGlass11.setName("panelGlass11"); // NOI18N
        panelGlass11.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel23.setText("Tgl. SEP :");
        jLabel23.setName("jLabel23"); // NOI18N
        jLabel23.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass11.add(jLabel23);

        DTPCari5.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-02-2023" }));
        DTPCari5.setDisplayFormat("dd-MM-yyyy");
        DTPCari5.setName("DTPCari5"); // NOI18N
        DTPCari5.setOpaque(false);
        DTPCari5.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass11.add(DTPCari5);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("s.d.");
        jLabel24.setName("jLabel24"); // NOI18N
        jLabel24.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass11.add(jLabel24);

        DTPCari6.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-02-2023" }));
        DTPCari6.setDisplayFormat("dd-MM-yyyy");
        DTPCari6.setName("DTPCari6"); // NOI18N
        DTPCari6.setOpaque(false);
        DTPCari6.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass11.add(DTPCari6);

        jLabel10.setText("Key Word :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass11.add(jLabel10);

        TCari2.setName("TCari2"); // NOI18N
        TCari2.setPreferredSize(new java.awt.Dimension(205, 23));
        TCari2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCari2KeyPressed(evt);
            }
        });
        panelGlass11.add(TCari2);

        BtnCari2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari2.setMnemonic('3');
        BtnCari2.setToolTipText("Alt+3");
        BtnCari2.setName("BtnCari2"); // NOI18N
        BtnCari2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari2ActionPerformed(evt);
            }
        });
        BtnCari2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari2KeyPressed(evt);
            }
        });
        panelGlass11.add(BtnCari2);

        jLabel11.setText("Record :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass11.add(jLabel11);

        LCount2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount2.setText("0");
        LCount2.setName("LCount2"); // NOI18N
        LCount2.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass11.add(LCount2);

        internalFrame6.add(panelGlass11, java.awt.BorderLayout.PAGE_END);

        TabRawat.addTab("Data SEP Rawat Inap", internalFrame6);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelGlass8.add(BtnPrint);

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

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnCari,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){            
            Map<String, Object> param = new HashMap<>(); 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            param.put("tanggal1",Valid.SetTgl(DTPCari1.getSelectedItem()+""));
            param.put("tanggal2",Valid.SetTgl(DTPCari2.getSelectedItem()+""));
            param.put("parameter","%"+TCari.getText().trim()+"%");
            Valid.MyReport("rptBridgingDaftar.jasper","report","::[ Data Bridging SEP ]::",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        query="";
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
//        if(akses.getform().equals("DlgReg")||akses.getform().equals("DlgIGD")||akses.getform().equals("DlgKasirRalan")||akses.getform().equals("DlgKamarInap")){
//            prb="";
//            no_peserta=Sequel.cariIsi("select no_peserta from pasien where no_rkm_medis=?",TNoRM.getText());
//            System.out.println("No.Peserta : "+no_peserta);
//            if(no_peserta.trim().equals("")){
//                JOptionPane.showMessageDialog(null,"Pasien tidak mempunyai kepesertaan BPJS");
//                dispose();
//            }else{
//                cekViaBPJSKartu.tampil(no_peserta);
//                if(cekViaBPJSKartu.informasi.equals("OK")){
//                    if(cekViaBPJSKartu.statusPesertaketerangan.equals("AKTIF")){
//                        TPasien.setText(cekViaBPJSKartu.nama);
//                        TglLahir.setText(cekViaBPJSKartu.tglLahir);
//                        JK.setText(cekViaBPJSKartu.sex);
//                        NoKartu.setText(no_peserta);
//                        JenisPeserta.setText(cekViaBPJSKartu.jenisPesertaketerangan);
//                        Status.setText(cekViaBPJSKartu.statusPesertaketerangan);
//                        KdPpkRujukan.setText(cekViaBPJSKartu.provUmumkdProvider);
//                        NmPpkRujukan.setText(cekViaBPJSKartu.provUmumnmProvider);
//                        if(cekViaBPJSKartu.hakKelaskode.equals("1")){
//                            Kelas.setSelectedIndex(0);
//                        }else if(cekViaBPJSKartu.hakKelaskode.equals("2")){
//                            Kelas.setSelectedIndex(1);
//                        }else if(cekViaBPJSKartu.hakKelaskode.equals("3")){
//                            Kelas.setSelectedIndex(2);
//                        }
//                        NoTelp.setText(cekViaBPJSKartu.mrnoTelepon);
//                        prb=cekViaBPJSKartu.informasiprolanisPRB.replaceAll("null","");
//                        NoRujukan.requestFocus();                                               
//                    }else{
//                        JOptionPane.showMessageDialog(null,"Status kepesertaan tidak aktif..!!");
//                        dispose();
//                    }
//                }else{
//                    dispose();
//                }                    
//            } 
//        }
    }//GEN-LAST:event_formWindowOpened

    private void ppSEPBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSEPBtnPrintActionPerformed
        if(tbDataSEP.getSelectedRow()!= -1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("prb",Sequel.cariIsi("select bpjs_prb.prb from bpjs_prb where bpjs_prb.no_sep=?",tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString()));
            param.put("logo",Sequel.cariGambar("select gambar.bpjs from gambar")); 
            param.put("parameter",tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString());
            param.put("finger",getStatusFinger(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),26).toString()));  
            System.out.println("msg finger "+getStatusFinger(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),26).toString()));
            if (tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),32).toString().equalsIgnoreCase("1.ya")){
                param.put("katarak","*PASIEN OPERASI KATARAK");
            } else {
                param.put("katarak","");
            }
            if(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),11).toString().equalsIgnoreCase("1. ranap")) {
                Valid.MyReport("rptBridgingSEP.jasper","report","::[ Cetak SEP ]::",param);
            }else{
                Valid.MyReport("rptBridgingSEP2.jasper","report","::[ Cetak SEP ]::",param);
            }                
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP yang mau dicetak...!!!!");
        }     
    }//GEN-LAST:event_ppSEPBtnPrintActionPerformed

    private void ppSEP1BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSEP1BtnPrintActionPerformed
        if(tbDataSEP.getSelectedRow()!= -1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("logo",Sequel.cariGambar("select gambar.bpjs from gambar")); 
            param.put("prb",Sequel.cariIsi("select bpjs_prb.prb from bpjs_prb where bpjs_prb.no_sep=?",tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString()));
            param.put("parameter",tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString());
//            param.put("finger2",getStatusFinger(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),20).toString()));  
            if(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),11).toString().equalsIgnoreCase("1. ranap")) {
                Valid.MyReport("rptBridgingSEP3.jasper","report","::[ Cetak SEP ]::",param);
            }else{
                Valid.MyReport("rptBridgingSEP4.jasper","report","::[ Cetak SEP ]::",param);
            }                
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP yang mau dicetak...!!!!");
        }
               
    }//GEN-LAST:event_ppSEP1BtnPrintActionPerformed

    private void ppSEP2BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSEP2BtnPrintActionPerformed
        if(tbDataSEP.getSelectedRow()!= -1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("norawat",tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),1).toString());
            param.put("prb",Sequel.cariIsi("select bpjs_prb.prb from bpjs_prb where bpjs_prb.no_sep=?",tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString()));
            param.put("noreg",Sequel.cariIsi("select no_reg from reg_periksa where no_rawat=?",tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),1).toString()));
            param.put("logo",Sequel.cariGambar("select gambar.bpjs from gambar")); 
            param.put("parameter",tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString());
//            param.put("finger2",getStatusFinger(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),20).toString()));  
                        
//                System.out.println("katarak "+tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),32).toString());
            if (tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),32).toString().equalsIgnoreCase("1.ya")){
                param.put("katarak","*PASIEN OPERASI KATARAK");
            } else {
                param.put("katarak","");
            }
            if(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),11).toString().equalsIgnoreCase("1. ranap")) {
                Valid.MyReport("rptBridgingSEP5.jasper","report","::[ Cetak SEP ]::",param);
            }else{
                Valid.MyReport("rptBridgingSEP6.jasper","report","::[ Cetak SEP ]::",param);
            }                
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP yang mau dicetak...!!!!");
        } 
    }//GEN-LAST:event_ppSEP2BtnPrintActionPerformed

    private void ppSEP3BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSEP3BtnPrintActionPerformed
        if(tbDataSEP.getSelectedRow()!= -1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            kddokter=Sequel.cariIsi("select reg_periksa.kd_dokter from reg_periksa where reg_periksa.no_rawat=?",tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),1).toString());
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("norawat",tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),1).toString());
            param.put("prb",Sequel.cariIsi("select bpjs_prb.prb from bpjs_prb where bpjs_prb.no_sep=?",tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString()));
            param.put("dokter",Sequel.cariIsi("select dokter.nm_dokter from dokter where dokter.kd_dokter=?",kddokter));
            param.put("noreg",Sequel.cariIsi("select no_reg from reg_periksa where no_rawat=?",tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),1).toString()));
            param.put("logo",Sequel.cariGambar("select gambar.bpjs from gambar")); 
            param.put("parameter",tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),0).toString());
//            param.put("finger2",getStatusFinger(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),20).toString()));  
            if (tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),32).toString().equalsIgnoreCase("1.ya")){
                param.put("katarak","*PASIEN OPERASI KATARAK");
            } else {
                param.put("katarak","");
            }
            if(tbDataSEP.getValueAt(tbDataSEP.getSelectedRow(),11).toString().equalsIgnoreCase("1. ranap")) {
                Valid.MyReport("rptBridgingSEP7RI.jasper","report","::[ Cetak SEP ]::",param);
            } else {
                Valid.MyReport("rptBridgingSEP8.jasper","report","::[ Cetak SEP ]::",param);              
            }                
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data SEP yang mau dicetak...!!!!");
        } 
    }//GEN-LAST:event_ppSEP3BtnPrintActionPerformed

    private void ppExportDataExcel(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppExportDataExcel
        try {
                                 
            File theDir = new File("C:\\DataSEP\\");
            if (!theDir.exists()){
                theDir.mkdirs();
            }
            String excelFileName = "C:\\DataSEP\\datasep-"+Valid.SetTgl(DTPCari1.getSelectedItem()+"").replace("-", "")+".xls";//name of excel file
            String sheetName = "Sheet1";//name of sheet
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet(sheetName) ;

            //Add first row
            HSSFRow row = sheet.createRow(0);
            HSSFCell cell00 = row.createCell(0);
            cell00.setCellValue("Tgl.");
            HSSFCell cell01 = row.createCell(1);
            cell01.setCellValue("No. SEP");
            HSSFCell cell02 = row.createCell(2);
            cell02.setCellValue("No. Kartu");
            HSSFCell cell03 = row.createCell(3);
            cell03.setCellValue("No. RM");
            HSSFCell cell04 = row.createCell(4);
            cell04.setCellValue("Nama");
            HSSFCell cell05 = row.createCell(5);
            cell05.setCellValue("L/P");
            HSSFCell cell06 = row.createCell(6);
            cell06.setCellValue("Umur");
            HSSFCell cell07 = row.createCell(7);
            cell07.setCellValue("Tgl. Terbit SEP");
            HSSFCell cell08 = row.createCell(8);
            cell08.setCellValue("Tgl. Kembali SEP");
            HSSFCell cell09 = row.createCell(9);
            cell09.setCellValue("Angka Lambat");
            HSSFCell cell10 = row.createCell(10);
            cell10.setCellValue("Keterangan");

            int rowNum = 0;
            try{
                ps=koneksi.prepareStatement(
                        "SELECT A.*, " +
                        "      @rownum := @rownum + 1 as row_number " +
                        "FROM ( " +
                        "	SELECT b.no_sep, b.nomr, b.nama_pasien, p.jk, TIMESTAMPDIFF(YEAR, b.tanggal_lahir, CURDATE()) as tahun, b.tglsep, p.no_peserta " +
                        "	FROM bridging_sep b " +
                        "	INNER JOIN pasien p ON p.no_rkm_medis = b.nomr " +
                        "	WHERE b.tglsep BETWEEN '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' AND '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' " +
                        ") A " +
                        "cross join (select @rownum := 0) r");
                try {
                    rs=ps.executeQuery();
                    while(rs.next()){
                        rowNum = Integer.valueOf(rs.getString("row_number"));
                        HSSFRow row1 = sheet.createRow(rowNum);
                        HSSFCell cell010 = row1.createCell(0);
                        cell010.setCellValue(Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                        HSSFCell cell011 = row1.createCell(1);
                        cell011.setCellValue(rs.getString("no_sep"));
                        HSSFCell cell012 = row1.createCell(2);
                        cell012.setCellValue(rs.getString("no_peserta"));
                        HSSFCell cell013 = row1.createCell(3);
                        cell013.setCellValue(rs.getString("nomr"));
                        HSSFCell cell014 = row1.createCell(4);
                        cell014.setCellValue(rs.getString("nama_pasien"));
                        HSSFCell cell015 = row1.createCell(5);
                        cell015.setCellValue(rs.getString("jk"));
                        HSSFCell cell016 = row1.createCell(6);
                        cell016.setCellValue(rs.getString("tahun")+" Th");
                        HSSFCell cell017 = row1.createCell(7);
                        cell017.setCellValue(rs.getString("tglsep"));
                        HSSFCell cell018 = row1.createCell(8);
                        cell018.setCellValue("");
                        HSSFCell cell019 = row1.createCell(9);
                        cell019.setCellValue("");
                        HSSFCell cell0110 = row1.createCell(10);
                        cell0110.setCellValue("");
                        rowNum =+ 1;
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

            FileOutputStream fileOut = new FileOutputStream(excelFileName);

            //write this workbook to an Outputstream.
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();
            System.out.println("Data SEP berhasil diexport di "+excelFileName);
            JOptionPane.showMessageDialog(null,"Data SEP berhasil diexport di "+excelFileName);
        } catch (IOException ex) {
            Logger.getLogger(BPJSDataSEP2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ppExportDataExcel

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
        query="";
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

    private void tbDataSEPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDataSEPKeyPressed
        
    }//GEN-LAST:event_tbDataSEPKeyPressed

    private void tbDataSEPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataSEPMouseClicked
        
    }//GEN-LAST:event_tbDataSEPMouseClicked

    private void tbDataSEP2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataSEP2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbDataSEP2MouseClicked

    private void tbDataSEP2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDataSEP2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbDataSEP2KeyPressed

    private void TCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCari2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TCari2KeyPressed

    private void BtnCari2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari2ActionPerformed
        tampil2();
    }//GEN-LAST:event_BtnCari2ActionPerformed

    private void BtnCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCari2KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            BPJSDataSEP2 dialog = new BPJSDataSEP2(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari2;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPCari5;
    private widget.Tanggal DTPCari6;
    private widget.Label LCount;
    private widget.Label LCount2;
    private widget.TextBox NoBalasan;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll2;
    private widget.TextBox TCari;
    private widget.TextBox TCari2;
    private javax.swing.JTabbedPane TabRawat;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame6;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.panelisi panelGlass11;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private javax.swing.JMenuItem ppExportDataExcel;
    private javax.swing.JMenuItem ppSEP;
    private javax.swing.JMenuItem ppSEP1;
    private javax.swing.JMenuItem ppSEP2;
    private javax.swing.JMenuItem ppSEP3;
    private widget.Table tbDataSEP;
    private widget.Table tbDataSEP2;
    // End of variables declaration//GEN-END:variables

    public void tampil() {        
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                    "select bridging_sep.no_sep, bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.tglsep,bridging_sep.tglrujukan,"+
                    "bridging_sep.no_rujukan,bridging_sep.kdppkrujukan,bridging_sep.nmppkrujukan,bridging_sep.kdppkpelayanan,bridging_sep.nmppkpelayanan,"+
                    "if(bridging_sep.jnspelayanan='1','1. Ranap','2. Ralan') as jnspelayanan,bridging_sep.catatan,bridging_sep.diagawal,bridging_sep.nmdiagnosaawal,bridging_sep.kdpolitujuan,"+
                    "bridging_sep.nmpolitujuan,if(bridging_sep.klsrawat='1','1. Kelas 1',if(bridging_sep.klsrawat='2','2. Kelas 2','3. Kelas 3')) as klsrawat,bridging_sep.klsnaik,"+
                    "bridging_sep.pembiayaan,bridging_sep.pjnaikkelas,bridging_sep.lakalantas,bridging_sep.user,bridging_sep.tanggal_lahir,"+
                    "bridging_sep.peserta,bridging_sep.jkel,bridging_sep.no_kartu,bridging_sep.tglpulang,bridging_sep.asal_rujukan,bridging_sep.eksekutif,bridging_sep.cob,"+
                    "bridging_sep.notelep,bridging_sep.katarak,bridging_sep.tglkkl,bridging_sep.keterangankkl,bridging_sep.suplesi,bridging_sep.no_sep_suplesi,bridging_sep.kdprop,"+
                    "bridging_sep.nmprop,bridging_sep.kdkab,bridging_sep.nmkab,bridging_sep.kdkec,bridging_sep.nmkec,bridging_sep.noskdp,bridging_sep.kddpjp,bridging_sep.nmdpdjp,"+
                    "bridging_sep.tujuankunjungan,bridging_sep.flagprosedur,bridging_sep.penunjang,bridging_sep.asesmenpelayanan,bridging_sep.kddpjplayanan,bridging_sep.nmdpjplayanan "+
                    "from bridging_sep where bridging_sep.jnspelayanan='2' and bridging_sep.tglsep between ? and ? "+query+(TCari.getText().trim().equals("")?"":" and (bridging_sep.no_sep like ? or "+
                    "bridging_sep.nomr like ? or bridging_sep.nama_pasien like ? or bridging_sep.nmppkrujukan like ? or bridging_sep.diagawal like ? or "+
                    "bridging_sep.nmdiagnosaawal like ? or bridging_sep.no_rawat like ? or bridging_sep.no_kartu like ? or bridging_sep.nmprop like ? or "+
                    "bridging_sep.nmkab like ? or bridging_sep.nmkec like ? or bridging_sep.nmdpdjp like ? or bridging_sep.asal_rujukan like ? or bridging_sep.notelep like ? "+
                    "or bridging_sep.nmpolitujuan like ?) ")+" order by bridging_sep.tglsep");
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
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    penunjang="";
                    switch(rs.getString("penunjang")){
                        case "1":
                            penunjang="1. Radioterapi";
                            break;
                        case "2":
                            penunjang="2. Kemoterapi";
                            break;
                        case "3":
                            penunjang="3. Rehabilitasi Medik";
                            break;
                        case "4":
                            penunjang="4. Rehabilitasi Psikososial";
                            break;
                        case "5":
                            penunjang="5. Transfusi Darah";
                            break;
                        case "6":
                            penunjang="6. Pelayanan Gigi";
                            break;
                        case "7":
                            penunjang="7. Laboratorium";
                            break;
                        case "8":
                            penunjang="8. USG";
                            break;
                        case "9":
                            penunjang="9. Farmasi";
                            break;
                        case "10":
                            penunjang="10. Lain-Lain";
                            break;
                        case "11":
                            penunjang="11. MRI";
                            break;
                        case "12":
                            penunjang="12. HEMODIALISA";
                            break;
                        default :
                            penunjang="";
                            break;
                    }
                    tabMode.addRow(new Object[]{
                        rs.getString("no_sep"),rs.getString("no_rawat"),rs.getString("nomr"),rs.getString("nama_pasien"),rs.getString("tglsep"),rs.getString("tglrujukan"),
                        rs.getString("no_rujukan"),rs.getString("kdppkrujukan"),rs.getString("nmppkrujukan"),rs.getString("kdppkpelayanan"),rs.getString("nmppkpelayanan"),
                        rs.getString("jnspelayanan"),rs.getString("catatan"),rs.getString("diagawal"),rs.getString("nmdiagnosaawal"),rs.getString("kdpolitujuan"),
                        rs.getString("nmpolitujuan"),rs.getString("klsrawat"),rs.getString("klsnaik").replaceAll("1","1. VVIP").replaceAll("2","2. VIP").
                        replaceAll("3","3. Kelas I").replaceAll("4","4. Kelas II").replaceAll("5","5. Kelas III").replaceAll("6","6. ICCU").replaceAll("7","7. ICU"),
                        rs.getString("pembiayaan").replaceAll("1","1. Pribadi").replaceAll("2","2. Pemberi Kerja").replaceAll("2","3. Asuransi Lain"),rs.getString("pjnaikkelas"),
                        rs.getString("lakalantas").replaceAll("0","0. Bukan KLL").replaceAll("1","1. KLL Bukan KK").replaceAll("2","2. KLL dan KK").replaceAll("3","3. KK"),
                        rs.getString("user"),rs.getString("tanggal_lahir"),rs.getString("peserta"),rs.getString("jkel"),rs.getString("no_kartu"),
                        rs.getString("tglpulang"),rs.getString("asal_rujukan"),rs.getString("eksekutif"),rs.getString("cob"),rs.getString("notelep"),rs.getString("katarak"),
                        rs.getString("tglkkl"),rs.getString("keterangankkl"),rs.getString("suplesi"),rs.getString("no_sep_suplesi"),rs.getString("kdprop"),rs.getString("nmprop"),
                        rs.getString("kdkab"),rs.getString("nmkab"),rs.getString("kdkec"),rs.getString("nmkec"),rs.getString("noskdp"),rs.getString("kddpjp"),rs.getString("nmdpdjp"),
                        rs.getString("tujuankunjungan").replaceAll("0","0. Normal").replaceAll("1","1. Prosedur").replaceAll("2","2. Konsul Dokter"),rs.getString("flagprosedur").
                        replaceAll("0","0. Prosedur Tidak Berkelanjutan").replaceAll("1","1. Prosedur dan Terapi Berkelanjutan"),penunjang,rs.getString("asesmenpelayanan").
                        replaceAll("1","1. Poli spesialis tidak tersedia pada hari sebelumnya").replaceAll("2","2. Jam Poli telah berakhir pada hari sebelumnya").
                        replaceAll("3","3. Spesialis yang dimaksud tidak praktek pada hari sebelumnya").replaceAll("4","4. Atas Instruksi RS").replaceAll("5","5. Tujuan Kontrol"),
                        rs.getString("kddpjplayanan"),rs.getString("nmdpjplayanan")
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
    
    public void tampil2() {        
        Valid.tabelKosong(tabModeRI);
        try{
            ps=koneksi.prepareStatement(
                    "select bridging_sep.no_sep, bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.tglsep,bridging_sep.tglrujukan,"+
                    "bridging_sep.no_rujukan,bridging_sep.kdppkrujukan,bridging_sep.nmppkrujukan,bridging_sep.kdppkpelayanan,bridging_sep.nmppkpelayanan,"+
                    "if(bridging_sep.jnspelayanan='1','1. Ranap','2. Ralan') as jnspelayanan,bridging_sep.catatan,bridging_sep.diagawal,bridging_sep.nmdiagnosaawal,bridging_sep.kdpolitujuan,"+
                    "bridging_sep.nmpolitujuan,if(bridging_sep.klsrawat='1','1. Kelas 1',if(bridging_sep.klsrawat='2','2. Kelas 2','3. Kelas 3')) as klsrawat,bridging_sep.klsnaik,"+
                    "bridging_sep.pembiayaan,bridging_sep.pjnaikkelas,bridging_sep.lakalantas,bridging_sep.user,bridging_sep.tanggal_lahir,"+
                    "bridging_sep.peserta,bridging_sep.jkel,bridging_sep.no_kartu,bridging_sep.tglpulang,bridging_sep.asal_rujukan,bridging_sep.eksekutif,bridging_sep.cob,"+
                    "bridging_sep.notelep,bridging_sep.katarak,bridging_sep.tglkkl,bridging_sep.keterangankkl,bridging_sep.suplesi,bridging_sep.no_sep_suplesi,bridging_sep.kdprop,"+
                    "bridging_sep.nmprop,bridging_sep.kdkab,bridging_sep.nmkab,bridging_sep.kdkec,bridging_sep.nmkec,bridging_sep.noskdp,bridging_sep.kddpjp,bridging_sep.nmdpdjp,"+
                    "bridging_sep.tujuankunjungan,bridging_sep.flagprosedur,bridging_sep.penunjang,bridging_sep.asesmenpelayanan,bridging_sep.kddpjplayanan,bridging_sep.nmdpjplayanan "+
                    "from bridging_sep where bridging_sep.jnspelayanan='1' and bridging_sep.tglsep between ? and ? "+query+(TCari.getText().trim().equals("")?"":" and (bridging_sep.no_sep like ? or "+
                    "bridging_sep.nomr like ? or bridging_sep.nama_pasien like ? or bridging_sep.nmppkrujukan like ? or bridging_sep.diagawal like ? or "+
                    "bridging_sep.nmdiagnosaawal like ? or bridging_sep.no_rawat like ? or bridging_sep.no_kartu like ? or bridging_sep.nmprop like ? or "+
                    "bridging_sep.nmkab like ? or bridging_sep.nmkec like ? or bridging_sep.nmdpdjp like ? or bridging_sep.asal_rujukan like ? or bridging_sep.notelep like ? "+
                    "or bridging_sep.nmpolitujuan like ?) ")+" order by bridging_sep.tglsep");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari5.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari6.getSelectedItem()+""));
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari2.getText().trim()+"%");
                    ps.setString(4,"%"+TCari2.getText().trim()+"%");
                    ps.setString(5,"%"+TCari2.getText().trim()+"%");
                    ps.setString(6,"%"+TCari2.getText().trim()+"%");
                    ps.setString(7,"%"+TCari2.getText().trim()+"%");
                    ps.setString(8,"%"+TCari2.getText().trim()+"%");
                    ps.setString(9,"%"+TCari2.getText().trim()+"%");
                    ps.setString(10,"%"+TCari2.getText().trim()+"%");
                    ps.setString(11,"%"+TCari2.getText().trim()+"%");
                    ps.setString(12,"%"+TCari2.getText().trim()+"%");
                    ps.setString(13,"%"+TCari2.getText().trim()+"%");
                    ps.setString(14,"%"+TCari2.getText().trim()+"%");
                    ps.setString(15,"%"+TCari2.getText().trim()+"%");
                    ps.setString(16,"%"+TCari2.getText().trim()+"%");
                    ps.setString(17,"%"+TCari2.getText().trim()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    penunjang="";
                    switch(rs.getString("penunjang")){
                        case "1":
                            penunjang="1. Radioterapi";
                            break;
                        case "2":
                            penunjang="2. Kemoterapi";
                            break;
                        case "3":
                            penunjang="3. Rehabilitasi Medik";
                            break;
                        case "4":
                            penunjang="4. Rehabilitasi Psikososial";
                            break;
                        case "5":
                            penunjang="5. Transfusi Darah";
                            break;
                        case "6":
                            penunjang="6. Pelayanan Gigi";
                            break;
                        case "7":
                            penunjang="7. Laboratorium";
                            break;
                        case "8":
                            penunjang="8. USG";
                            break;
                        case "9":
                            penunjang="9. Farmasi";
                            break;
                        case "10":
                            penunjang="10. Lain-Lain";
                            break;
                        case "11":
                            penunjang="11. MRI";
                            break;
                        case "12":
                            penunjang="12. HEMODIALISA";
                            break;
                        default :
                            penunjang="";
                            break;
                    }
                    tabModeRI.addRow(new Object[]{
                        rs.getString("no_sep"),rs.getString("no_rawat"),rs.getString("nomr"),rs.getString("nama_pasien"),rs.getString("tglsep"),rs.getString("tglrujukan"),
                        rs.getString("no_rujukan"),rs.getString("kdppkrujukan"),rs.getString("nmppkrujukan"),rs.getString("kdppkpelayanan"),rs.getString("nmppkpelayanan"),
                        rs.getString("jnspelayanan"),rs.getString("catatan"),rs.getString("diagawal"),rs.getString("nmdiagnosaawal"),rs.getString("kdpolitujuan"),
                        rs.getString("nmpolitujuan"),rs.getString("klsrawat"),rs.getString("klsnaik").replaceAll("1","1. VVIP").replaceAll("2","2. VIP").
                        replaceAll("3","3. Kelas I").replaceAll("4","4. Kelas II").replaceAll("5","5. Kelas III").replaceAll("6","6. ICCU").replaceAll("7","7. ICU"),
                        rs.getString("pembiayaan").replaceAll("1","1. Pribadi").replaceAll("2","2. Pemberi Kerja").replaceAll("2","3. Asuransi Lain"),rs.getString("pjnaikkelas"),
                        rs.getString("lakalantas").replaceAll("0","0. Bukan KLL").replaceAll("1","1. KLL Bukan KK").replaceAll("2","2. KLL dan KK").replaceAll("3","3. KK"),
                        rs.getString("user"),rs.getString("tanggal_lahir"),rs.getString("peserta"),rs.getString("jkel"),rs.getString("no_kartu"),
                        rs.getString("tglpulang"),rs.getString("asal_rujukan"),rs.getString("eksekutif"),rs.getString("cob"),rs.getString("notelep"),rs.getString("katarak"),
                        rs.getString("tglkkl"),rs.getString("keterangankkl"),rs.getString("suplesi"),rs.getString("no_sep_suplesi"),rs.getString("kdprop"),rs.getString("nmprop"),
                        rs.getString("kdkab"),rs.getString("nmkab"),rs.getString("kdkec"),rs.getString("nmkec"),rs.getString("noskdp"),rs.getString("kddpjp"),rs.getString("nmdpdjp"),
                        rs.getString("tujuankunjungan").replaceAll("0","0. Normal").replaceAll("1","1. Prosedur").replaceAll("2","2. Konsul Dokter"),rs.getString("flagprosedur").
                        replaceAll("0","0. Prosedur Tidak Berkelanjutan").replaceAll("1","1. Prosedur dan Terapi Berkelanjutan"),penunjang,rs.getString("asesmenpelayanan").
                        replaceAll("1","1. Poli spesialis tidak tersedia pada hari sebelumnya").replaceAll("2","2. Jam Poli telah berakhir pada hari sebelumnya").
                        replaceAll("3","3. Spesialis yang dimaksud tidak praktek pada hari sebelumnya").replaceAll("4","4. Atas Instruksi RS").replaceAll("5","5. Tujuan Kontrol"),
                        rs.getString("kddpjplayanan"),rs.getString("nmdpjplayanan")
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
    
    public void setNoRm2(String norwt, Date tgl1,String status,String kdpoli,String namapoli,String kddokter) {
        TCari.setText(norwt);      
    }
    
    public void setNoRm(String norwt) {
        TCari.setText(norwt);          
    }
      
    public void setNoRm3(String norwt, Date tgl1) {
        TCari.setText(norwt);
        DTPCari1.setDate(tgl1);
        DTPCari2.setDate(tgl1);
    }
    
    public void tutupInput(){
        TabRawat.setSelectedIndex(1);
    }
    
    public String getStatusFinger(String no_peserta) {
        String msg="";
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add("X-Cons-ID",koneksiDB.CONSIDAPIBPJS());
	    utc=String.valueOf(api.GetUTCdatetimeAsString());
	    headers.add("X-Timestamp",utc);
	    headers.add("X-Signature",api.getHmac(utc));
            headers.add("user_key",koneksiDB.USERKEYAPIBPJS());
	    requestEntity = new HttpEntity(headers);
            URL = link+"SEP/FingerPrint/Peserta/"+no_peserta+"/TglPelayanan/"+dateFormat.format(date);	
            System.out.println("URL finger "+URL);
            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
            nameNode = root.path("metaData");
            System.out.println("code : "+nameNode.path("code").asText());
            System.out.println("message : "+nameNode.path("message").asText());
            if(nameNode.path("code").asText().equals("200")){
                response = mapper.readTree(api.Decrypt(root.path("response").asText(),utc));
                //response = root.path("response");
                msg=response.path("status").asText();
                
            }else {
                JOptionPane.showMessageDialog(null,nameNode.path("message").asText());                
            }   
        } catch (Exception ex) {
            System.out.println("Notifikasi Peserta : "+ex);
            if(ex.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(null,"Koneksi ke server BPJS terputus...!");
            }
        }
        return msg;
    }
}
