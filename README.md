# TPE2-POD
2019 2C Grupo 2: Della Sala - Giorgi - Rodriguez - Santoflaminio

Instrucciones

1. Desde la carpeta principal ejecutar en terminal ./start.sh (puede que previamente sea necesario realizar 'chmod +x start.sh')

2. Luego ejecutar ./server.sh

3. En otra terminal dirigirse a client/target/tp-client-1.0-SNAPSHOT/

4. Ejecutar la query deseada como se indica a continuacion:


-- Query 1:
	./query1.sh -Daddresses='xx.x.x.x:xxxx' -DinPath='directorio_entrada' -DoutPath='directorio_salida'

-- Query 2:
	./query2.sh -Daddresses='xx.x.x.x:xxxx' -DinPath='directorio_entrada' -DoutPath='directorio_salida' -Dn=N

-- Query 3:
	./query3.sh -Daddresses='xx.x.x.x:xxxx' -DinPath='directorio_entrada' -DoutPath='directorio_salida' 

-- Query 4:
	./query4.sh -Daddresses='xx.x.x.x:xxxx' -DinPath='directorio_entrada' -DoutPath='directorio_salida' -Dn=N -Doaci=CODE

-- Query 5:
	./query5.sh -Daddresses='xx.x.x.x:xxxx' -DinPath='directorio_entrada' -DoutPath='directorio_salida' -Dn=N

-- Query 6:
	./query6.sh -Daddresses='xx.x.x.x:xxxx' -DinPath='directorio_entrada' -DoutPath='directorio_salida' -Dmin=MIN


Aclaraciones:
	
	- DinPath debe ser el directorio donde se encuentran aeropuertos.csv y movimientos.csv
	- Dn debe ser un numero entero
	- Dmin debe ser un numero entero
	- Doaci debe ser un oaci valido
