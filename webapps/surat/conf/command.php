<?php
    if(strpos($_SERVER['REQUEST_URI'],"conf")){
        exit(header("Location:../index.php"));
    }
	
    function title(){
            $judul ="Surat Menyurat SIMRS Khanza --)(*!!@#$%";
            $judul = preg_replace("[^A-Za-z0-9_\-\./,|]"," ",$judul);
            $judul = str_replace(array('.','-','/',',')," ",$judul);
            $judul = trim($judul);
            echo "$judul";	
    }

    function cekSessiAdmin() {
        if (isset($_SESSION['ses_admin_suratsurat'])) {
            return true;
        } else {
            return false;
        }
    }


    function cekUser() {
        if (isset($_SESSION['ses_admin_suratsurat'])) {
            return true;
        } else {
            return false;
        }
    }

    function adminAktif() {
        if (cekSessiAdmin()) {
            return $_SESSION['ses_admin_suratsurat'];
        }
    }

    function isGuest() {
        if (cekSessiAdmin()) {
            return false;
        } else {
            return true;
        }
    }	

    function formProtek() {
            $aksi=isset($_GET['act'])?$_GET['act']:NULL;
            if (!cekUser()) {
                    $form = array ('HomeAdmin','List','Input','List2','Input2');
                            foreach ($form as $page) {
                                    if ($aksi==$page) {
                                            echo "<META HTTP-EQUIV = 'Refresh' Content = '0; URL = ?act=Home'>";
                                            exit;
                                            break;
                                    }
                            }
                    }		

    }

    function actionPages() {
        $aksi=isset($_REQUEST['act'])?$_REQUEST['act']:NULL;
            formProtek();
            switch ($aksi) {
                    case 'HomeAdmin'	 : include_once('pages/kontak.php'); break;
                    case 'List'              : include_once('pages/list.php'); break;
                    case 'Input'             : include_once('pages/input.php'); break;
                    case 'List2'             : include_once('pages/list2.php'); break;
                    case 'Input2'            : include_once('pages/input2.php'); break;

                    default			 : include_once('pages/kontak.php');

            }
    }
?>
