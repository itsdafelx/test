DROP DATABASE IF EXISTS CalendarEvents;
CREATE DATABASE CalendarEvents;
USE CalendarEvents;

CREATE TABLE Event (
	EventID INT NOT NULL AUTO_INCREMENT,
    EventTitle NVARCHAR(30),
    EventLocation NVARCHAR(30),
    EventDate DATE,
    IsOnline BOOLEAN,
    EventDescription NVARCHAR(30),
    PRIMARY KEY(EventID)
);

INSERT INTO Event(EventTitle, EventLocation, EventDate, IsOnline, EventDescription)
VALUES('IDDE kurzus', 'BBTE Főépület', '2024-10-28', false, 'Új anyag, kvíz, kód, stb.');

select * from Event