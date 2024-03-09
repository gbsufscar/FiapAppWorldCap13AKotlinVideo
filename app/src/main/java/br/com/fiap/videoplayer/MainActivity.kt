package br.com.fiap.videoplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import br.com.fiap.videoplayer.ui.theme.VideoPlayerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VideoPlayerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VideoPlayer()
                }
            }
        }
    }
}

// Função responsável para montar o reprodutor de vídeo
@Composable
fun VideoPlayer() {
    // Variável que contém a URL do vídeo
    val videoUrl =
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

    // Contexto da aplicação
    val context = LocalContext.current

    // Instância do ExoPlayer responsável por reproduzir o vídeo
    val player = ExoPlayer.Builder(context).build()

    // View do Player, responsável por exibir o vídeo e seus controles
    val playerView = PlayerView(context)

    // Item de mídia que será reproduzida
    val mediaItem = MediaItem.fromUri(videoUrl)

    // Booleano que indica se o vídeo está disponível para ser reproduzido
    val playWhenReay by remember {
        mutableStateOf(true)
    }

    // Configuração do Player
    player.setMediaItem(mediaItem)

    // Atribui o player à view do player para que o vídeo seja exibido
    playerView.player = player

    // Inicia a corrotina resposável pela reprodução do vídeo em segundo plano
    // Toca o vídeo sem interferir na thread principal
    LaunchedEffect(player) {
        player.prepare() // Prepara o vídeo para ser reproduzido
        player.playWhenReady = playWhenReay // Inicia a reprodução do vídeo
    }

    // Responsável por colocar o componente playerView na hierarquia de exibição na Interface do Usuário
    AndroidView(factory = {
        playerView
    })

}