package net.shirojr.screen.element;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.shirojr.AcanthusPocket;

//FIXME: Button hitbox and / or whole screen has a weird offset from the elements
public class QuickTimeButton extends ButtonWidget {
    private int positionX;
    private int positionY;
    public QuickTimeButton(int x, int y, int width, int height, Text message, PressAction onPress, NarrationSupplier narrationSupplier) {
        super(x, y, width, height, message, onPress, narrationSupplier);
        this.positionX = x + 5;
        this.positionY = y + 5;
        this.width = width;
        this.height = height;
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, new Identifier(AcanthusPocket.MOD_ID, "textures/screens/basic_pocket_gui.png"));


        int u = 176; //v
        int v = 0; //u
        if (this.isHovered()) {
            u += 10;
        }
        this.drawTexture(matrices, positionX, positionY, u , v, 10, 14);
    }
}
