<?php

  $codigo = filter_input(INPUT_POST, "codigo");
  $pin = filter_input(INPUT_POST, "pin");
  $mysqli = new mysqli("localhost", "root", "", "bdsistemapos");
  $result = mysqli_query($mysqli, "SELECT * FROM cuentas WHERE codigo='".$codigo."' AND pin = '".$pin."'");

  if($data = mysqli_fetch_array($result)){
    echo '1';
  }else{
    echo '0';
  }

 ?>
