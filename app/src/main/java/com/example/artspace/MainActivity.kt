package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtSpaceLayout()
                }
            }
        }
    }
}

/**
 * Le composable principal avec état
 * Il gère la logique de l'application et "hisse" l'état pour les composables sans état.
 */
@Composable
fun ArtSpaceLayout(modifier: Modifier = Modifier) {
    // État pour suivre l'œuvre d'art actuellement affichée.
    // C'est le seul état de l'application, géré ici.
    var currentArtworkId by remember { mutableStateOf(1) }

    // Logique pour déterminer quelles ressources afficher en fonction de l'état
    val artworkImageResource = when (currentArtworkId) {
        1 -> R.drawable.art_1
        2 -> R.drawable.art_2
        3 -> R.drawable.art_3
        else -> R.drawable.art_1
    }

    val artworkTitleResource = when (currentArtworkId) {
        1 -> R.string.artwork1_title
        2 -> R.string.artwork2_title
        3 -> R.string.artwork3_title
        else -> R.string.artwork1_title
    }

    val artworkArtistAndYearResource = when (currentArtworkId) {
        1 -> R.string.artwork1_artist_year
        2 -> R.string.artwork2_artist_year
        3 -> R.string.artwork3_artist_year
        else -> R.string.artwork1_artist_year
    }

    // Le Column organise les composables enfants
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        // 1. Appel du Composable SANS ÉTAT pour l'image
        ArtworkWall(artworkImageResource = artworkImageResource)

        // 2. Appel du Composable SANS ÉTAT pour la description
        ArtworkDescriptor(
            titleResource = artworkTitleResource,
            artistAndYearResource = artworkArtistAndYearResource
        )

        // 3. Appel du Composable SANS ÉTAT pour les boutons,
        // en lui passant la logique de mise à jour de l'état.
        DisplayController(
            onPreviousClick = {
                if (currentArtworkId == 1) {
                    currentArtworkId = 3 // Revient à la dernière
                } else {
                    currentArtworkId--
                }
            },
            onNextClick = {
                if (currentArtworkId == 3) {
                    currentArtworkId = 1 // Revient à la première
                } else {
                    currentArtworkId++
                }
            }
        )
    }
}

// Les Composables sans état
@Composable
fun ArtworkWall(artworkImageResource: Int, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(elevation = 8.dp),
        border = BorderStroke(2.dp, Color.LightGray)
    ) {
        Image(
            painter = painterResource(id = artworkImageResource),
            contentDescription = stringResource(id = R.string.artwork1_title), // Mieux pour l'accessibilité
            modifier = Modifier.padding(32.dp),
            contentScale = ContentScale.Fit
        )
    }
}


@Composable
fun ArtworkDescriptor(
    titleResource: Int,
    artistAndYearResource: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color(0xFFECEBF4))
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = titleResource),
            fontSize = 24.sp,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = stringResource(id = artistAndYearResource),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun DisplayController(
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = onPreviousClick,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = stringResource(R.string.previous_button))
        }

        Spacer(modifier = Modifier.width(24.dp))

        Button(
            onClick = onNextClick,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = stringResource(R.string.next_button))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ArtSpacePreview() {
    ArtSpaceTheme {
        ArtSpaceLayout()
    }
}