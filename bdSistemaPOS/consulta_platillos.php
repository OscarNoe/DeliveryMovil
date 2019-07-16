<?php
  $mysqli = new mysqli("localhost", "root", "", "bdsistemapos");
  mysqli_set_charset($mysqli, 'utf8');
  $json=array();

    $consulta = "SELECT * FROM producto";
    $result= mysqli_query($mysqli, $consulta);

    while($registro = mysqli_fetch_array($result)){
      $json['producto'][]=$registro;
      //echo $registro['id'];
    }
    mysqli_close($mysqli);
    echo json_encode($json);

 ?>
