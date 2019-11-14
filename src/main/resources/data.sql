INSERT INTO USER(id, birth_date, name) Values( 1, parseDateTime('29/08/1988','dd/MM/yyyy'), 'Josyas Lima' );
INSERT INTO USER(id, birth_date, name) Values( 2, parseDateTime('01/07/1990','dd/MM/yyyy'), 'Malaquias Lima' );


INSERT INTO POST(id, message, post_date, user_id) Values(1, 'Amazing', parseDateTime('29/08/2000','dd/MM/yyyy'), 1 );
INSERT INTO POST(id, message, post_date, user_id) Values(2, 'Wonderful', parseDateTime('29/10/2000','dd/MM/yyyy'), 1 );
INSERT INTO POST(id, message, post_date, user_id) Values(3, 'Marvelous', parseDateTime('29/11/2000','dd/MM/yyyy'), 2 );
INSERT INTO POST(id, message, post_date, user_id) Values(4, 'Top', parseDateTime('29/12/2000','dd/MM/yyyy'), 2 );