using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SqlClient;
using Eventos;

namespace interfaceBD
{
    public partial class EventoMain : Form
    {
        private SqlConnection cn;
        private int currentEvent;
        private int currentConcerto;

        public EventoMain()
        {
            InitializeComponent();
        }

        // event handler
        private void Form1_Load(object sender, EventArgs e)
        {
            cn = getSGBDConnection();
            adicionar.Visible = false;
            Update.Visible = false;
            HideOverviewSection();
            HideConcertosSection();
            HideBandasSection();
            HideMusicosSection();
            loadEventos();
        }
        private void SearchBtnOverview_Click(object sender, EventArgs e)
        {
            if (searchbarOverview.Text != "")
            {
                if (!verifySGBDConnection())
                    return;
                SqlCommand cmd = new SqlCommand("SELECT * from getOverviewByNome(" + searchbarOverview.Text + ")", cn);
                if (radiobtn_nome_OVERVIEW.Checked)
                {
                    cmd = new SqlCommand("SELECT * from getOverviewByNome('" + searchbarOverview.Text + "')", cn);
                }
                else if (radiobtn_numdias_OVERVIEW.Checked)
                {
                    cmd = new SqlCommand("SELECT * from getOverviewByNumdias(" + searchbarOverview.Text + ")", cn);
                }
                else if (radiobtn_banda_OVERVIEW.Checked)
                {
                    cmd = new SqlCommand("SELECT * from getOverviewByBanda('" + searchbarOverview.Text + "')", cn);
                }
                else
                {
                    MessageBox.Show("Please select a filter1!");
                }
                try
                {
                    SqlDataReader reader = cmd.ExecuteReader();
                    listBox2.Items.Clear();
                    listBox2.Items.Add(Overview.Fline());
                    while (reader.Read())
                    {
                        Overview O = new Overview();
                        O.Nome = reader["nome"].ToString();
                        O.Numdias = reader["numdias"].ToString();
                        O.Numbilhetes = reader["numbilhetes"].ToString();
                        O.Dataini = reader["dataini"].ToString().Split(' ')[0];
                        O.Banda = reader["BANDA"].ToString();
                        O.Promotor = reader["PROMOTOR"].ToString().Split(' ')[0]; ;
                        O.Duracao = reader["duracao"].ToString();
                        listBox2.Items.Add(O);
                    }
                    cn.Close();
                }
                catch (Exception ex)
                {
                    ;
                }
            }
            else if (radiobtn_filtar_DateTime_OVERVIEW.Checked)
            {
                if (!verifySGBDConnection())
                    return;
                SqlCommand cmd;
                String start = String.Format("{0:u}", filtros_start_date.Value);
                String end = String.Format("{0:u}", filtros_end_date.Value);
                cmd = new SqlCommand("SELECT * from getEventosInBetween('" + start.ToString().Split(' ')[0] + "', '" + end.ToString().Split(' ')[0] + "')", cn);
                SqlDataReader reader = cmd.ExecuteReader();
                listBox2.Items.Clear();
                listBox2.Items.Add(Evento.Fline());
                while (reader.Read())
                {
                    Evento E = new Evento();
                    E.Id = reader["id"].ToString();
                    E.Name = reader["nome"].ToString();
                    E.Numdias = reader["numdias"].ToString();
                    E.NumBilhetes = reader["numbilhetes"].ToString();
                    E.Dataini = reader["dataini"].ToString().Split(' ')[0];
                    E.Datafim = reader["datafim"].ToString().Split(' ')[0]; ;
                    E.Cc_promotor = reader["cc_promotor"].ToString();
                    E.DataProposta = reader["dataproposta"].ToString().Split(' ')[0]; ;
                    E.Cc_stageManager = reader["cc_stageManager"].ToString();
                    listBox2.Items.Add(E);
                }
                cn.Close();
            }
            else
            {
                LoadOverview();
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            if (searchbar.Text != "")
            { 
                if (!verifySGBDConnection())
                    return;
                SqlCommand cmd = new SqlCommand("SELECT * from getEventosById(" + searchbar.Text + ")", cn);
                if (idrb.Checked)
                {
                    cmd = new SqlCommand("SELECT * from getEventosById(" + searchbar.Text + ")", cn);
                }
                else if (namerb.Checked)
                {
                    cmd = new SqlCommand("SELECT * from getEventosByNome('" + searchbar.Text + "')", cn);
                }
                else if (promotorrb.Checked)
                {
                    cmd = new SqlCommand("SELECT * from getEventosByPromotor(" + searchbar.Text + ")", cn);
                }
                else if (stageManagerrb.Checked)
                {
                    cmd = new SqlCommand("SELECT * from getEventosByStageManager(" + searchbar.Text + ")", cn);
                }
                else
                {
                    MessageBox.Show("Please select a filter!");
                }
                try
                {
                    SqlDataReader reader = cmd.ExecuteReader();
                    listBox1.Items.Clear();
                    listBox1.Items.Add(Evento.Fline());
                    while (reader.Read())
                    {
                        Evento E = new Evento();
                        E.Id = reader["id"].ToString();
                        E.Name = reader["nome"].ToString();
                        E.Numdias = reader["numdias"].ToString();
                        E.NumBilhetes = reader["numbilhetes"].ToString();
                        E.Dataini = reader["dataini"].ToString().Split(' ')[0];
                        E.Datafim = reader["datafim"].ToString().Split(' ')[0]; ;
                        E.Cc_promotor = reader["cc_promotor"].ToString();
                        E.DataProposta = reader["dataproposta"].ToString().Split(' ')[0]; ;
                        E.Cc_stageManager = reader["cc_stageManager"].ToString();
                        listBox1.Items.Add(E);
                    }
                    cn.Close();
                } catch (Exception ex)
                {
                    ;
                }
            }
            else
            {
                loadEventos();
            }
        }
        private void searchBtn_CONCERTOS_Click(object sender, EventArgs e)
        {
            if (search_bar_CONCERTOS.Text != "")
            {
                if (!verifySGBDConnection())
                    return;
                SqlCommand cmd = new SqlCommand("SELECT * from getConcertosByNomeEvento('" + search_bar_CONCERTOS.Text + "')", cn);
                if (radiotn_CONCERTOS_nome.Checked)
                {
                    cmd = new SqlCommand("SELECT * from getConcertosByNomeEvento('" + search_bar_CONCERTOS.Text + "')", cn);
                }
                else if (radiobtn_CONCERTOS_banda.Checked)
                {
                    cmd = new SqlCommand("SELECT * from getConcertosByBanda('" + search_bar_CONCERTOS.Text + "')", cn);
                }
                else
                {
                    MessageBox.Show("Please select a filter!");
                }
                try
                {
                    if (!verifySGBDConnection())
                        return;
                    SqlDataReader reader = cmd.ExecuteReader();
                    listBox3.Items.Clear();
                    listBox3.Items.Add(Concerto.Fline());
                    while (reader.Read())
                    {
                        Concerto C = new Concerto();
                        C.Id = reader["id"].ToString();
                        C.Nome = reader["nome"].ToString();
                        C.Id_evento = reader["id_evento"].ToString();
                        C.Banda = reader["banda"].ToString();
                        C.Id_banda = reader["id_banda"].ToString().Split(' ')[0];
                        C.Dataini = reader["datatimeini"].ToString().Split(' ')[0];
                        C.Duracao = reader["duracao"].ToString();
                        C.Id_scheck = reader["id_sc"].ToString();
                        C.Duracao_scheck = reader["duracao_sc"].ToString();
                        C.Datatimeini_scheck = reader["datatimeini_sc"].ToString();
                        listBox3.Items.Add(C);
                    }
                    cn.Close();
                }
                catch (Exception ex)
                {
                    ;
                }
            }
            else if (filtrar_CONCERTOS_HORAS.Checked)
            {
                if (!verifySGBDConnection())
                    return;
                string start = (dtpicker_hora_start_CONCERTOS.Value).ToString("HH:mm:ss");
                string end = (dtpicker_time_end_CONCERTOS.Value).ToString("HH:mm:ss");
                SqlCommand cmd = new SqlCommand("SELECT * from getConcertosDuracaoInBetween('" + start + "', '" + end + "')", cn);
                SqlDataReader reader = cmd.ExecuteReader();
                listBox3.Items.Clear();
                listBox3.Items.Add(Concerto.Fline());
                while (reader.Read())
                {
                    Concerto C = new Concerto();
                    C.Id = reader["id"].ToString();
                    C.Nome = reader["nome"].ToString();
                    C.Id_evento = reader["id_evento"].ToString();
                    C.Banda = reader["banda"].ToString();
                    C.Id_banda = reader["id_banda"].ToString().Split(' ')[0];
                    C.Dataini = reader["datatimeini"].ToString().Split(' ')[0];
                    C.Duracao = reader["duracao"].ToString();
                    C.Id_scheck = reader["id_sc"].ToString();
                    C.Duracao_scheck = reader["duracao_sc"].ToString();
                    C.Datatimeini_scheck = reader["datatimeini_sc"].ToString();
                    listBox3.Items.Add(C);
                }
                reader.Close();
                cn.Close();
            }
            else
            {
                LoadConcertos();
            }
        }

        private void searchBtn_MUSICOS_Click(object sender, EventArgs e)
        {
            if (searchbox_MUSICOS.Text != "")
            {
                if (!verifySGBDConnection())
                    return;
                SqlCommand cmd = new SqlCommand("SELECT * from getMusicoByCC(" + searchbox_MUSICOS.Text + ")", cn);

                if (radiotn_MUSICOS_CC.Checked)
                {
                    cmd = new SqlCommand("SELECT * from getMusicoByCC(" + searchbox_MUSICOS.Text + ")", cn);
                }
                else if (radiotn_MUSICOS_nome.Checked)
                {
                    cmd = new SqlCommand("SELECT * from getMusicoByName('" + searchbox_MUSICOS.Text + "')", cn);
                }
                else if (radiotn_MUSICOS_nomeArt.Checked)
                {
                    cmd = new SqlCommand("SELECT * from getMusicoByArstName('" + searchbox_MUSICOS.Text + "')", cn);
                }
                else
                {
                    MessageBox.Show("Please select a filter!");
                }
                try
                {
                    SqlDataReader reader = cmd.ExecuteReader();
                    listBox_musicos.Items.Clear();
                    listBox_musicos.Items.Add(Musico.Fline());
                    while (reader.Read())
                    {
                        Musico M = new Musico();
                        M.NumCC = reader["numCC"].ToString();
                        M.Nome = reader["nome"].ToString();
                        M.NomeArt = reader["nomeArst"].ToString();
                        M.Email = reader["email"].ToString();
                        M.Sexo = reader["sexo"].ToString();
                        M.Banda = reader["banda"].ToString();
                        listBox_musicos.Items.Add(M);
                    }
                    cn.Close();
                }
                catch (Exception ex)
                {
                    ;
                }
            }
            else
            {
                LoadMusicos();
            }
        }

        private void searchbtn_BANDAS_Click(object sender, EventArgs e)
        {
            if (searchBox_BANDAS.Text != "")
            {
                if (!verifySGBDConnection())
                    return;
                SqlCommand cmd = new SqlCommand("SELECT * from getBandaById('" + searchBox_BANDAS.Text + "')", cn);

                if (radiobtn_BANDAS_id.Checked)
                {
                    cmd = new SqlCommand("SELECT * from getBandaById('" + searchBox_BANDAS.Text + "')", cn);
                }
                else if (radiobtn_BANDAS_nome.Checked)
                {
                    cmd = new SqlCommand("SELECT * from getBandaByNome('" + searchBox_BANDAS.Text + "')", cn);
                }
                else if (radiobtn_BANDAS_genero.Checked)
                {
                    cmd = new SqlCommand("SELECT * from getBandaByGenre('" + searchBox_BANDAS.Text + "')", cn);
                }
                else
                {
                    MessageBox.Show("Please select a filter!");
                }
                try
                {
                    SqlDataReader reader = cmd.ExecuteReader();
                    listBox_bandas.Items.Clear();
                    listBox_bandas.Items.Add(Banda.Fline());
                    while (reader.Read())
                    {
                        Banda B = new Banda();
                        B.Id = reader["id"].ToString();
                        B.Nome = reader["nome"].ToString();
                        B.Telefone = "+" + reader["telefone"].ToString();
                        B.Email = reader["email"].ToString();
                        B.NumElem = reader["numElem"].ToString();
                        B.Genero = reader["genero"].ToString();
                        listBox_bandas.Items.Add(B);
                    }
                    cn.Close();
                }
                catch (Exception ex)
                {
                    ;
                }
            }
            else
            {
                LoadBandas();
            }
        }


        private void listBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (listBox1.SelectedIndex > 0)
            {
                currentEvent = listBox1.SelectedIndex;
                ShowEvent();
            }
        }

        private void listBox3_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (listBox3.SelectedIndex > 0)
            {
                currentConcerto = listBox3.SelectedIndex;
                ShowConcerto();
            }
        }


        // aux
        public void ShowEvent()
        {
            if (listBox1.Items.Count == 0 | currentEvent < 0)
                return;
            Evento E = new Evento();
            E = (Evento)listBox1.Items[currentEvent];
            idEvento.Text = E.Id;
            nomeEvento.Text = E.Name;
            datainicio.Value = DateTime.Parse(E.Dataini);
            numdias.Text = E.Numdias;
            numbilhetes.Text = E.NumBilhetes;
            datefim.Value = DateTime.Parse(E.Datafim);
            ccpromotor.Text = E.Cc_promotor;
            ccstageManager.Text = E.Cc_stageManager;
            dataproposta.Text = E.DataProposta;
        }

        public void ShowConcerto()
        {
            if (listBox3.Items.Count == 0 | currentConcerto < 0)
                return;
            Concerto C = new Concerto();
            C = (Concerto)listBox3.Items[currentConcerto];
            id_concerto_input.Text = C.Id;
            dataini_dtpicker_data_input.Value = DateTime.Parse(C.Dataini);
            id_evento_input.Text = C.Id_evento;
            id_banda_input.Text = C.Id_banda;
            id_soundcheck_input.Text = C.Id_scheck;
            try
            {
                dtpicker_duracao_soundcheck.Value = DateTime.Parse(C.Duracao_scheck);
            } catch (Exception ex)
            {
                ;
            }
            try
            {
                dtpicker_dataini_soundcheck.Value = DateTime.Parse(C.Datatimeini_scheck);
            }
            catch (Exception ex)
            {
                ;
            }
        }

        public void ClearFields()
        {
            idEvento.Text = "";
            nomeEvento.Text = "";
            datainicio.Text = "";
            numdias.Text = "";
            numbilhetes.Text = "";
            datefim.Text = "";
            ccpromotor.Text = "";
            ccstageManager.Text = "";
            dataproposta.Text = "";
        }

        public void ClearConcertosFields()
        {
            id_concerto_input.Text = "";
            dataini_dtpicker_data_input.Text = "";
            horaini_concerto_dtpicker.Text = "";
            duracao_dtpicker_hora.Text = "";
            id_banda_input.Text = "";
            id_evento_input.Text = "";
            // soundcheck
            id_soundcheck_input.Text = "";
            dtpicker_duracao_soundcheck.Text = "";
            dtpicker_dataini_soundcheck.Text = "";
            dtpicker_horaini_soundcheck.Text = "";
        }

        private SqlConnection getSGBDConnection()
        {
            string dbServer = "tcp:mednat.ieeta.pt\\SQLSERVER,8101";
            string dbName = "p2g5";
            string userName = "p2g5";
            string userPass = "PedroJoaoS_Q_L";
            return new SqlConnection("Data Source = " + dbServer + " ;" + "Initial Catalog = " + dbName + "; uid = " + userName + ";" + "password = " + userPass);

        }

        private bool verifySGBDConnection()
        {
            if (cn == null)
                cn = getSGBDConnection();

            if (cn.State != ConnectionState.Open)
                cn.Open();

            return cn.State == ConnectionState.Open;
        }

        // adicionar, editar, remover evento
        private void adicionarEvento_Click(object sender, EventArgs e)
        {
            if (!verifySGBDConnection())
                return;

            SqlCommand cmd = new SqlCommand("SELECT count(*) as entry from EM.EVENTO", cn);
            SqlDataReader reader = cmd.ExecuteReader();
            adicionar.Visible = true;
            adicionarEvento.Visible = false;
            deleteEvento.Visible = false;
            EditEvent.Visible = false;
            ClearFields();
            if (reader.Read())
            {
                idEvento.Text = ((int)(reader["entry"]) + 1).ToString();
            }
            cn.Close();
        }

        private void adicionar_Click(object sender, EventArgs e)
        {
            Evento E = new Evento();
            E.Id = idEvento.Text;
            E.Name = nomeEvento.Text;
            E.NumBilhetes = numbilhetes.Text;
            E.Numdias = numdias.Text;
            E.Dataini = datainicio.Text;
            E.Datafim = datefim.Text;
            E.DataProposta = dataproposta.Text;
            E.Cc_stageManager = ccstageManager.Text;
            E.Cc_promotor = ccpromotor.Text;
            // adicionar evento à bd
            SubmitEvento(E);
        }


        private void SubmitEvento(Evento E)
        {
            if (!verifySGBDConnection())
                return;
            SqlCommand cmd = new SqlCommand("create_evento", cn);
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.Parameters.Clear();
            cmd.Parameters.AddWithValue("@id", E.Id);
            cmd.Parameters.AddWithValue("@nome", E.Name);
            cmd.Parameters.AddWithValue("@numdias", E.Numdias);
            cmd.Parameters.AddWithValue("@dataini", DateTime.Parse(E.Dataini));
            cmd.Parameters.AddWithValue("@datafim", DateTime.Parse(E.Datafim));
            cmd.Parameters.AddWithValue("@numbilhetes", E.NumBilhetes);
            cmd.Parameters.AddWithValue("@cc_promotor", E.Cc_promotor);
            cmd.Parameters.AddWithValue("@dataproposta", DateTime.Parse(E.DataProposta));
            cmd.Parameters.AddWithValue("@cc_stageManager", E.Cc_stageManager);
            cmd.Connection = cn;

            try
            {
                cmd.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                throw new Exception("Failed to update contact in database. \n ERROR MESSAGE: \n" + ex.Message);
            }
            finally
            {
                MessageBox.Show("Event \"" + E.Name + "\" with succsess");
                cn.Close();
            }
            adicionar.Visible = false;
            adicionarEvento.Visible = true;
            EditEvent.Visible = true;
            deleteEvento.Visible = true;
            loadEventos();
        }

        private void EditEvent_Click(object sender, EventArgs e)
        {
            currentEvent = listBox1.SelectedIndex;
            if (currentEvent <= 0)
            {
                MessageBox.Show("Please select a contact to edit");
                return;
            }
            else
            {
                ShowEvent();
            }
            EditEvent.Visible = false;
            deleteEvento.Visible = false;
            adicionarEvento.Visible = false;
            adicionar.Visible = false;
            Update.Visible = true;
        }

        private void Update_Click(object sender, EventArgs e)
        {
            try
            {
                UpdateEvent();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            EditEvent.Visible = true;
            adicionarEvento.Visible = true;
            adicionar.Visible = false;
            Update.Visible = false;
            deleteEvento.Visible = true;
        }

        public void UpdateEvent()
        {
            Evento E = new Evento();
            try
            {
                E.Id = idEvento.Text;
                E.Name = nomeEvento.Text;
                E.NumBilhetes = numbilhetes.Text;
                E.Numdias = numdias.Text;
                E.Dataini = datainicio.Text;
                E.Datafim = datefim.Text;
                E.DataProposta = dataproposta.Text;
                E.Cc_stageManager = ccstageManager.Text;
                E.Cc_promotor = ccpromotor.Text;

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                return;
            }

            int rows = 0;

            if (!verifySGBDConnection())
                return;
            SqlCommand cmd = new SqlCommand("alter_evento", cn);
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.Parameters.Clear();
            cmd.Parameters.AddWithValue("@id", E.Id);
            cmd.Parameters.AddWithValue("@nome", E.Name);
            cmd.Parameters.AddWithValue("@numdias", E.Numdias);
            cmd.Parameters.AddWithValue("@dataini", DateTime.Parse(E.Dataini));
            cmd.Parameters.AddWithValue("@numBilhetes", E.NumBilhetes);
            cmd.Parameters.AddWithValue("@datafim", DateTime.Parse(E.Datafim));
            cmd.Parameters.AddWithValue("@dataproposta", DateTime.Parse(E.DataProposta));
            cmd.Parameters.AddWithValue("@cc_promotor", E.Cc_promotor);
            cmd.Parameters.AddWithValue("@cc_stagemanager", E.Cc_stageManager);
            cmd.Connection = cn;
            try
            {
                rows = cmd.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                throw new Exception("Failed to update contact in database. \n ERROR MESSAGE: \n" + ex.Message);
            }
            finally
            {
                if (rows == 1)
                    MessageBox.Show("Update OK");
                else
                    MessageBox.Show("Update NOT OK");

                cn.Close();
            }
            loadEventos();
        }

        private void deleteEvento_Click(object sender, EventArgs e)
        {
            if (listBox1.SelectedIndex > -1)
            {
                try
                {
                    RemoveEvent(((Evento)listBox1.SelectedItem).Id);
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message);
                    return;
                }
                listBox1.Items.RemoveAt(listBox1.SelectedIndex);
                if (currentEvent == listBox1.Items.Count)
                    currentEvent = listBox1.Items.Count - 1;
                if (currentEvent == -1)
                {
                    ClearFields();
                    MessageBox.Show("There are no more Eventos");
                }
                else
                {
                    ShowEvent();
                }
            }
            EditEvent.Visible = true;
            adicionarEvento.Visible = true;
            adicionar.Visible = false;
            Update.Visible = false;
            deleteEvento.Visible = true;
        }

        public void RemoveEvent(String eventoId)
        {
            if (!verifySGBDConnection())
                return;

            SqlCommand cmd = new SqlCommand("delete_evento_byId", cn);
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.Parameters.Clear();
            cmd.Parameters.AddWithValue("@id", eventoId);
            cmd.Connection = cn;

            try
            {
                cmd.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                throw new Exception("Failed to delete contact in database. \n ERROR MESSAGE: \n" + ex.Message);
            }
            finally
            {
                cn.Close();
            }
        }

        // adicionar, editar, remover Concerto
        private void adicionarConcertoBtn_Click(object sender, EventArgs e)
        {
            addConcertoBtn.Visible = true;
            adicionarConcertoBtn.Visible = false;
            editConcertoBtn.Visible = false;
            deleteConcertoBtn.Visible = false;
            ClearConcertosFields();
        }

        private void addConcertoBtn_Click(object sender, EventArgs e)
        {
            Concerto C = new Concerto();
            C.Id = id_concerto_input.Text;
            C.Dataini = dataini_dtpicker_data_input.Text;
            C.Duracao = duracao_dtpicker_hora.Text;
            C.Id_banda = id_banda_input.Text;
            C.Id_evento = id_evento_input.Text;
            // soundcheck
            C.Id_scheck = id_soundcheck_input.Text;
            C.Datatimeini_scheck = dtpicker_dataini_soundcheck.Text;
            C.Duracao_scheck = dtpicker_duracao_soundcheck.Text;
            C.Id_scheck = id_soundcheck_input.Text;
            C.Duracao_scheck = dtpicker_duracao_soundcheck.Text;
            C.Datatimeini_scheck = dtpicker_dataini_soundcheck.Text;
            // adicionar evento à bd
            SubmitConcerto(C);
        }

        private void SubmitConcerto(Concerto C)
        {
            if (!verifySGBDConnection())
                return;
            SqlCommand cmd = new SqlCommand("create_concerto", cn);
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.Parameters.Clear();
            cmd.Parameters.AddWithValue("@id", C.Id);
            cmd.Parameters.AddWithValue("@datatimeini", DateTime.Parse(C.Dataini));
            cmd.Parameters.AddWithValue("@duracao", C.Duracao);
            cmd.Parameters.AddWithValue("@id_banda", C.Id_banda);
            cmd.Parameters.AddWithValue("@id_evento", C.Id_evento);
            // soundcheck
            cmd.Parameters.AddWithValue("@id_soundcheck", C.Id_scheck);
            cmd.Parameters.AddWithValue("@duracao_s", C.Duracao_scheck);
            cmd.Parameters.AddWithValue("@data_s", DateTime.Parse(C.Datatimeini_scheck));
            cmd.Connection = cn;
            try
            {
                cmd.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                throw new Exception("Failed to update contact in database. \n ERROR MESSAGE: \n" + ex.Message);
            }
            finally
            {
                MessageBox.Show("Concerto \"" + C.Id + "\" with succsess");
                cn.Close();
            }
            addConcertoBtn.Visible = false;
            adicionarConcertoBtn.Visible = true;
            editConcertoBtn.Visible = true;
            deleteConcertoBtn.Visible = true;
        }

        private void editConcertoBtn_Click(object sender, EventArgs e)
        {
            currentConcerto = listBox3.SelectedIndex;
            if (currentConcerto <= 0)
            {
                MessageBox.Show("Please select a Concerto to edit");
                return;
            }
            else
            {
                ShowConcerto();
            }
            editConcertoBtn.Visible = false;
            deleteConcertoBtn.Visible = false;
            adicionarConcertoBtn.Visible = false;
            addConcertoBtn.Visible = false;
            UpdateConcertoBtn.Visible = true;
        }

        private void UpdateConcertoBtn_Click(object sender, EventArgs e)
        {
            Concerto C = new Concerto();
            try
            {
                C.Id = id_concerto_input.Text;
                C.Dataini = dataini_dtpicker_data_input.Text + " " + horaini_concerto_dtpicker.Text;
                C.Duracao = duracao_dtpicker_hora.Text;
                C.Id_banda = id_banda_input.Text;
                C.Id_evento = id_evento_input.Text;
                C.Id_scheck = id_soundcheck_input.Text;
                C.Duracao_scheck = dtpicker_duracao_soundcheck.Text;
                C.Datatimeini_scheck = dtpicker_dataini_soundcheck.Text;
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                return;
            }

            int rows = 0;

            if (!verifySGBDConnection())
                return;
            SqlCommand cmd = new SqlCommand("alter_concerto", cn);
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.Parameters.Clear();
            cmd.Parameters.AddWithValue("@id", C.Id);
            cmd.Parameters.AddWithValue("@datatimeini", DateTime.Parse(C.Dataini));
            cmd.Parameters.AddWithValue("@duracao", C.Duracao);
            cmd.Parameters.AddWithValue("@id_banda", C.Id_banda);
            cmd.Parameters.AddWithValue("@id_evento", C.Id_evento);
            // soundcheck
            cmd.Parameters.AddWithValue("@id_soundcheck", C.Id_scheck);
            cmd.Parameters.AddWithValue("@soundcheck_duracao", C.Duracao_scheck);
            cmd.Parameters.AddWithValue("@soundcheck_datetime", DateTime.Parse(C.Datatimeini_scheck));
            cmd.Connection = cn;

            try
            {
                rows = cmd.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                throw new Exception("Failed to update Concert in database. \n ERROR MESSAGE: \n" + ex.Message);
            }
            finally
            {
                if (rows == 1)
                    MessageBox.Show("Update OK");
                else
                    MessageBox.Show("Update NOT OK");

                cn.Close();
            }
            addConcertoBtn.Visible = false;
            UpdateConcertoBtn.Visible = false;
            adicionarConcertoBtn.Visible = true;
            editConcertoBtn.Visible = true;
            deleteConcertoBtn.Visible = true;
        }

        private void deleteConcertoBtn_Click(object sender, EventArgs e)
        {
            if (listBox3.SelectedIndex > -1)
            {
                try
                {
                    RemoveConcerto(((Concerto)listBox3.SelectedItem).Id);
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message);
                    return;
                }
                listBox3.Items.RemoveAt(listBox3.SelectedIndex);
                if (currentEvent == listBox3.Items.Count)
                    currentEvent = listBox3.Items.Count - 1;
                if (currentEvent == -1)
                {
                    ClearConcertosFields();
                    MessageBox.Show("There are no more Concertos");
                }
                else
                {
                    ShowConcerto();
                }
            }
            editConcertoBtn.Visible = true;
            adicionarConcertoBtn.Visible = true;
            addConcertoBtn.Visible = false;
            UpdateConcertoBtn.Visible = false;
            deleteConcertoBtn.Visible = true;
        }

        public void RemoveConcerto(String concertoId)
        {
            if (!verifySGBDConnection())
                return;
            SqlCommand cmd = new SqlCommand("delete_evento_byId", cn);
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.Parameters.Clear();
            cmd.Parameters.AddWithValue("@id", concertoId);
            cmd.Connection = cn;

            try
            {
                cmd.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                throw new Exception("Failed to delete contact in database. \n ERROR MESSAGE: \n" + ex.Message);
            }
            finally
            {
                cn.Close();
            }
        }

        // load from database
        private void loadEventos()
        {
            loadEventos("SELECT * FROM EM.EVENTO ORDER BY id");
        }

        private void loadEventos(String query)
        {
            if (!verifySGBDConnection())
                return;

            SqlCommand cmd = new SqlCommand(query, cn);
            SqlDataReader reader = cmd.ExecuteReader();
            listBox1.Items.Clear();
            listBox1.Items.Add(Evento.Fline());
            while (reader.Read())
            {
                Evento E = new Evento();
                E.Id = reader["id"].ToString();
                E.Name = reader["nome"].ToString();
                E.Numdias = reader["numdias"].ToString();
                E.NumBilhetes = reader["numbilhetes"].ToString();
                E.Dataini = reader["dataini"].ToString().Split(' ')[0];
                E.Datafim = reader["datafim"].ToString().Split(' ')[0]; ;
                E.Cc_promotor = reader["cc_promotor"].ToString();
                E.DataProposta = reader["dataproposta"].ToString().Split(' ')[0]; ;
                E.Cc_stageManager = reader["cc_stageManager"].ToString();
                listBox1.Items.Add(E);
            }
            cn.Close();
        }

        private void LoadConcertos()
        {
            if (!verifySGBDConnection())
                return;

            SqlCommand cmd = new SqlCommand("select * from EM.V_CONCERTOS", cn);
            SqlDataReader reader = cmd.ExecuteReader();
            listBox3.Items.Clear();
            listBox3.Items.Add(Concerto.Fline());
            while (reader.Read())
            {
                Concerto C = new Concerto();
                C.Id = reader["id"].ToString();
                C.Nome = reader["nome"].ToString();
                C.Id_evento = reader["id_evento"].ToString();
                C.Banda = reader["banda"].ToString();
                C.Id_banda = reader["id_banda"].ToString().Split(' ')[0];
                C.Dataini = reader["datatimeini"].ToString().Split(' ')[0];
                C.Duracao = reader["duracao"].ToString();
                C.Id_scheck = reader["id_sc"].ToString();
                C.Duracao_scheck = reader["duracao_sc"].ToString();
                C.Datatimeini_scheck = reader["datatimeini_sc"].ToString();
                listBox3.Items.Add(C);
            }
            cn.Close();
        }
        private void LoadOverview()
        {
            if (!verifySGBDConnection())
                return;

            SqlCommand cmd = new SqlCommand("SELECT * FROM EM.V_OVERVIEW", cn);
            SqlDataReader reader = cmd.ExecuteReader();
            listBox2.Items.Clear();
            listBox2.Items.Add(Overview.Fline());
            while (reader.Read())
            {
                Overview O = new Overview();
                O.Nome = reader["nome"].ToString();
                O.Numdias = reader["numdias"].ToString();
                O.Numbilhetes = reader["numbilhetes"].ToString();
                O.Dataini = reader["dataini"].ToString().Split(' ')[0];
                O.Banda = reader["BANDA"].ToString();
                O.Promotor = reader["PROMOTOR"].ToString().Split(' ')[0]; ;
                O.Duracao = reader["duracao"].ToString();
                listBox2.Items.Add(O);
            }
            cn.Close();
        }
        private void LoadBandas()
        {
            if (!verifySGBDConnection())
                return;

            SqlCommand cmd = new SqlCommand("SELECT * FROM EM.V_BANDAS", cn);
            SqlDataReader reader = cmd.ExecuteReader();
            listBox_bandas.Items.Clear();
            listBox_bandas.Items.Add(Banda.Fline());
            while (reader.Read())
            {
                Banda B = new Banda();
                B.Id = reader["id"].ToString();
                B.Nome = reader["nome"].ToString();
                B.Telefone = "+" + reader["telefone"].ToString();
                B.Email = reader["email"].ToString();
                B.NumElem = reader["numElem"].ToString();
                B.Genero = reader["genero"].ToString();
                listBox_bandas.Items.Add(B);
            }
            cn.Close();
        }
        private void LoadMusicos()
        {
            if (!verifySGBDConnection())
                return;

            SqlCommand cmd = new SqlCommand("SELECT * FROM EM.V_MUSICOS", cn);
            SqlDataReader reader = cmd.ExecuteReader();
            listBox_musicos.Items.Clear();
            listBox_musicos.Items.Add(Musico.Fline());
            while (reader.Read())
            {
                Musico M = new Musico();
                M.NumCC = reader["numCC"].ToString();
                M.Nome = reader["nome"].ToString();
                M.NomeArt = reader["nomeArst"].ToString();
                M.Email = reader["email"].ToString();
                M.Sexo = reader["sexo"].ToString();
                M.Banda = reader["banda"].ToString();
                listBox_musicos.Items.Add(M);
            }
            cn.Close();
        }

        // menu buttons
        private void button1_Click(object sender, EventArgs e)
        {
            HideOverviewSection();
            HideConcertosSection();
            HideBandasSection();
            HideMusicosSection();
            listBox1.Show();
            label1.Show();
            label2.Show();
            label3.Show();
            label4.Show();
            label5.Show();
            label6.Show();
            label7.Show();
            label8.Show();
            Label1X.Show();             
            nomeEvento.Show();
            idEvento.Show();
            datainicio.Show();
            datefim.Show();
            dataproposta.Show();
            numdias.Show();
            numbilhetes.Show();
            ccpromotor.Show();
            ccstageManager.Show();
            groupBox1.Show();
            idrb.Show();
            namerb.Show();
            promotorrb.Show();
            stageManagerrb.Show();
            searchbar.Show();
            SearchBtn_Eventos.Show();
            deleteEvento.Show();
            adicionarEvento.Show();
            EditEvent.Show();
        }
        private void button3_Click(object sender, EventArgs e)
        {
            HideEventoSection();
            HideBandasSection();
            HideConcertosSection();
            HideMusicosSection();
            listBox2.Show();
            groupboxOverview.Show();
            LoadOverview();

        }
        private void concertobtn_Click(object sender, EventArgs e)
        {
            HideOverviewSection();
            HideEventoSection();
            HideBandasSection();
            HideMusicosSection();
            listBox3.Show();
            deleteConcertoBtn.Show();
            editConcertoBtn.Show();
            deleteConcertoBtn.Show();
            adicionarConcertoBtn.Show();
            id_concerto_label.Show();
            id_concerto_input.Show();
            dataini_concerto_label.Show();
            dataini_dtpicker_data_input.Show();
            horaini_concerto_label.Show();
            horaini_concerto_dtpicker.Show();
            duracao_dtpicker.Show();
            duracao_dtpicker_hora.Show();
            id_banda_label.Show();
            id_banda_input.Show();
            id_evento_label.Show();
            id_evento_input.Show();
            id_soundcheck_label.Show();
            id_soundcheck_input.Show();
            groupBox_Concertos.Show();
            Soundcheck_groupbox_concertos.Show();
            LoadConcertos();
        }
        private void bandabtn_Click(object sender, EventArgs e)
        {
            HideConcertosSection();
            HideEventoSection();
            HideOverviewSection();
            HideMusicosSection();
            listBox_bandas.Show();
            groupBox_bandas.Show();
            LoadBandas();
        }
        private void musicosbtn_Click(object sender, EventArgs e)
        {
            HideConcertosSection();
            HideEventoSection();
            HideOverviewSection();
            HideBandasSection();
            listBox_musicos.Show();
            groupBox_musicos.Show();
            LoadMusicos();
        }
        // section Hide
        private void HideEventoSection()
        {
            listBox1.Hide();
            label1.Hide();
            label2.Hide();
            label3.Hide();
            label4.Hide();
            label5.Hide();
            label6.Hide();
            label7.Hide();
            label8.Hide();
            Label1X.Hide();
            nomeEvento.Hide();
            idEvento.Hide();
            datainicio.Hide();
            datefim.Hide();
            dataproposta.Hide();
            numdias.Hide();
            numbilhetes.Hide();
            ccpromotor.Hide();
            ccstageManager.Hide();
            groupBox1.Hide();
            idrb.Hide();
            namerb.Hide();
            promotorrb.Hide();
            stageManagerrb.Hide();
            searchbar.Hide();
            SearchBtn_Eventos.Hide();
            deleteEvento.Hide();
            adicionarEvento.Hide();
            EditEvent.Hide();
            Update.Hide();
            adicionar.Hide();
        }

        private void HideOverviewSection()
        {
            listBox2.Hide();
            groupboxOverview.Hide();
        }

        private void HideConcertosSection()
        {
            listBox3.Hide();
            listBox2.Hide();
            editConcertoBtn.Hide();
            deleteConcertoBtn.Hide();
            addConcertoBtn.Hide();
            editConcertoBtn.Hide();
            UpdateConcertoBtn.Hide();
            adicionarConcertoBtn.Hide();
            id_concerto_label.Hide();
            id_concerto_input.Hide();
            dataini_concerto_label.Hide();
            dataini_dtpicker_data_input.Hide();
            horaini_concerto_label.Hide();
            horaini_concerto_dtpicker.Hide();
            duracao_dtpicker.Hide();
            duracao_dtpicker_hora.Hide();
            id_banda_label.Hide();
            id_banda_input.Hide();
            id_evento_label.Hide();
            id_evento_input.Hide();
            id_soundcheck_label.Hide();
            id_soundcheck_input.Hide();
            groupBox_Concertos.Hide();
            Soundcheck_groupbox_concertos.Hide();
        }
        private void HideBandasSection()
        {
            listBox_bandas.Hide();
            groupBox_bandas.Hide();
        }

        private void HideMusicosSection()
        {
            listBox_musicos.Hide();
            groupBox_musicos.Hide();
        }

        private void label10_Click(object sender, EventArgs e)
        {

        }

        private void groupBox_bandas_Enter(object sender, EventArgs e)
        {

        }

        private void groupBox_Concertos_Enter(object sender, EventArgs e)
        {

        }

        private void radiotn_CONCERTOS_nome_CheckedChanged(object sender, EventArgs e)
        {

        }

        private void listBox_bandas_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        private void radioButton4_CheckedChanged(object sender, EventArgs e)
        {

        }
    }
}
