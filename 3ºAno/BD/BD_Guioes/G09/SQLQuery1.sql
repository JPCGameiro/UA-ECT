DBCC FREEPROCCACHE;
DBCC DROPCLEANBUFFERS;
GO

SELECT * FROM Production.WorkOrder;
GO

SELECT * FROM Production.WorkOrder WHERE WorkOrderID=1234;
GO

SELECT * FROM Production.WorkOrder WHERE StartDate = '2007-06-25';
GO


SELECT WorkOrderID, StartDate FROM Production.WorkOrder WHERE ProductID = 757;
GO

SELECT WorkOrderID, StartDate FROM Production.WorkOrder WHERE ProductID = 945;
GO

SELECT WorkOrderID FROM Production.WorkOrder WHERE ProductID = 945 AND StartDate = '2006-01-04';
GO


SELECT WorkOrderID, StartDate FROM Production.WorkOrder WHERE ProductID = 945 AND StartDate = '2006-01-04';
GO