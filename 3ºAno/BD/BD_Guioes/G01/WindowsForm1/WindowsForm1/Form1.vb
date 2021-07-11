Imports System.Data.SqlClient

Public Class Form1


	Private Sub Label1_Click(sender As Object, e As EventArgs)

	End Sub

	Private Sub ListBox1_SelectedIndexChanged(sender As Object, e As EventArgs)

	End Sub

	Private Sub CheckBox1_CheckedChanged(sender As Object, e As EventArgs)

	End Sub

	Private Sub LB1_SelectedIndexChanged(sender As Object, e As EventArgs)

	End Sub

	Private Sub Button1_Click(sender As Object, e As EventArgs) Handles Button1.Click
		Dim CN As New SqlConnection("Data Source = " + TextBox1.Text + " ;" + "Initial Catalog = " + "" + "; uid = " + TextBox2.Text + ";" + "password = " + TextBox3.Text)

		CN.Open()
		MsgBox(GetTableContent(CN))
		CN.Close()
	End Sub

	Private Sub Button2_Click(sender As Object, e As EventArgs) Handles Button2.Click
		TestDBConnection(TextBox1.Text, "", TextBox2.Text, TextBox3.Text)
	End Sub

	Private Sub Label1_Click_1(sender As Object, e As EventArgs) Handles Label1.Click

	End Sub

	Private Sub Label2_Click(sender As Object, e As EventArgs) Handles Label2.Click

	End Sub

	Private Sub TextBox1_TextChanged(sender As Object, e As EventArgs) Handles TextBox1.TextChanged

	End Sub


	Private Sub TestDBConnection(ByVal dbServer As String, ByVal dbName As String, ByVal userName As String, ByVal userPass As String)

		Dim CN As New SqlConnection("Data Source = " + dbServer + " ;" + "Initial Catalog = " + dbName + "; uid = " + userName + ";" + "password = " + userPass)

		Try
			CN.Open()
			If CN.State = ConnectionState.Open Then
				MsgBox("Successful connection to database " + CN.Database + " on the " + CN.DataSource + " server", MsgBoxStyle.OkOnly, "Connection Test")
			End If

		Catch ex As Exception
			MsgBox("FAILED TO OPEN CONNECTION TO DATABASE DUE TO THE FOLLOWING ERROR" + vbCrLf + ex.Message, MsgBoxStyle.Critical, "Connection Test")
		End Try

		If CN.State = ConnectionState.Open Then CN.Close()
	End Sub

	Private Function GetTableContent(ByVal CN As SqlConnection) As String
		If CN.State = ConnectionState.Closed Then Return "SHIT"


		Dim str As String = ""
		Dim cnt As Integer = 1
		Dim sqlcmd As New SqlCommand("SELECT * FROM Hello", CN)
		Dim reader As SqlDataReader
		reader = sqlcmd.ExecuteReader

		While reader.Read
			str += cnt.ToString + " - " + Convert.ToString(reader.Item("MsgID")) + ", "
			str += Convert.ToString(reader.Item("MsgSubject"))
			str += vbCrLf
			cnt += 1
		End While

		Return str
	End Function

End Class
