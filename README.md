# AnonymousChatRMI
Distributed Programming Exam of Università degli Studi di Salerno

Create a distribuited program with Java RMI with a client-server architecture that implement a selective chat with anonymity on request.  
Is a classic chat where the server also participates, but is possible send messages from each partecipant to a subset of these (selective chat). Moreover the server, that is a chat partecipant, have the power to make the chat anonymous.  
Every string writed in chat they are sent in broadcast indicating the sender.  
### The list of command
- "**!lista**": shows the list of participants
- "**!scrivi**": to write a message to a subset of participants an example of this command 
John Doe write  
` !scrivi Francesco:Maria:Vittorio Come state?  `

the message write by John Dow will be sent only to Francesco, Maria and Vittorio
- "**!anonimo**" (only the server) make the sender anonymous
- "**!nonanonimo**" (only the server) only the server)
