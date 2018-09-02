# springbootaxonmongo
a simple application to use axon with springboot and mongo for using event sourcing

<h2>Pre-setup </h2>
<p>make sure your mongodb is running in your local. for simple commands to start with 
<br> mongod —dbpath /data/db —port 27017 </br> 
</P>
<h2>Testing using curl </h2>
<p>make sure your curl is setup in your environment accordingly. simple commands
<br>
  <ul><li>
create account<br>
curl  --data {\"name\":\"Lotche\"} -H "Content-Type: application/json" localhost:10201/accounts
    </li>
    <li>
deposit account<br>
curl -i -X PUT --data 10 -H "Content-Type: application/json" localhost:10201/accounts/d3e5b0c0-f3d3-4487-8be6-6ee6b644bd2d/balance
    </li>
<li>
withdraw account <br>
curl -i -X PUT --data -5 -H "Content-Type: application/json" localhost:10201/accounts/d3e5b0c0-f3d3-4487-8be6-6ee6b644bd2d/balance
    </li>
    <li>

over withdraw </br>
curl -i -X PUT --data -500 -H "Content-Type: application/json" localhost:10201/accounts/d3e5b0c0-f3d3-4487-8be6-6ee6b644bd2d/balance
</li>
<li>
delete account <br>
curl -i -X DELETE -H "Content-Type: application/json" localhost:10201/accounts/d3e5b0c0-f3d3-4487-8be6-6ee6b644bd2d
</li>
<li>
list all events <br>
curl  -H "Content-Type: application/json" localhost:10201/accounts/d3e5b0c0-f3d3-4487-8be6-6ee6b644bd2d/events
</li>
</P>

<br>
<h2>dependencies </h2>
<p>
<ul>
  <li> spring-boot-starter-web </li>
  <li> spring-boot-starter-data-mongodb </li>
  <li> axon-spring-boot-starter </li>
  <li> axon-mongo </li>
<ul>  
  </p>
  
  <p>
  This is a very simple example to understand and try event sourcing with axon framwork and springboot using mongodb repository to store events. 
  </p>
