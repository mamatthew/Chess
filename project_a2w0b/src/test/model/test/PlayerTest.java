package model.test;

import model.Board;
import model.Color;
import model.Player;
import model.pieces.Knight;
import model.pieces.Queen;
import model.pieces.Rook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    Player player;

    @BeforeEach
    public void runBefore() {
        player = new Player("Matthew", new ArrayList<>());
    }

    @Test
    public void testConstructor() {
        assertEquals("Matthew", player.getName());
        assertEquals(0, player.getMaterial());
        assertEquals(0, player.getControlledPieces().size());
        assertEquals(0, player.getCapturedPieces().size());
    }

    @Test
    public void testCalculateMaterial() {
        player.getCapturedPieces().add(new Knight(Color.WHITE, new Board()));
        assertEquals(3, player.calculateMaterial());
        player.getCapturedPieces().add(new Queen(Color.WHITE, new Board()));
        assertEquals(12, player.calculateMaterial());
    }

    @Test
    public void testCalculateMaterialNewMaterial() {
        player.getCapturedPieces().add(new Knight(Color.WHITE, new Board()));

        player.getCapturedPieces().add(new Queen(Color.WHITE, new Board()));
        assertEquals(12, player.calculateMaterial());
    }


    @Test
    public void testAddToCaptured() {
        player.addToCaptured(new Rook(Color.WHITE, new Board()));
        assertEquals(1, player.getCapturedPieces().size());
        assertTrue(player.getCapturedPieces().get(0) instanceof Rook);
    }
}
