package fr.osalys.plugin.util;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;

import java.util.Arrays;

public class ItemBuilder {

    private final ItemStack is;
    public ItemBuilder(Material m) {
        this(m, 1);
    }
    public ItemBuilder(ItemStack is) {
        this.is = is;
    }
    public ItemBuilder(Material m, int amount) {
        is = new ItemStack(m, amount);
    }
    public ItemBuilder(Material m, int amount, short meta){
        is = new ItemStack(m, amount, meta);
    }
    public ItemBuilder clone() {
        return new ItemBuilder(is);
    }

    public ItemBuilder setGlow(boolean glow){
        if (glow) {
            addEnchant(Enchantment.KNOCKBACK, 1);
            addItemFlag(ItemFlag.HIDE_ENCHANTS);
        } else {
            ItemMeta meta = is.getItemMeta();
            assert meta != null;
            for (Enchantment enchantment : meta.getEnchants().keySet()){
                meta.removeEnchant(enchantment);
            }
        }
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable){
        ItemMeta meta = is.getItemMeta();
        assert meta != null;
        meta.setUnbreakable(unbreakable);
        is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setBannerColor (DyeColor color){
        BannerMeta meta = (BannerMeta) is.getItemMeta();
        assert meta != null;
        meta.setBaseColor(color);
        is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setName(String name){
        ItemMeta meta = is.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setSkull(Player owner){
        SkullMeta meta = (SkullMeta) is.getItemMeta();
        assert meta != null;
        meta.setOwningPlayer(owner);
        is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag flag) {
        ItemMeta meta = is.getItemMeta();
        assert meta != null;
        meta.addItemFlags(flag);
        is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setDurability(short dur){
        ItemMeta meta = is.getItemMeta();
        assert meta != null;
        ((Damageable) meta).setDamage(dur);
        is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment ench, int level){
        ItemMeta meta = is.getItemMeta();
        assert meta != null;
        meta.addEnchant(ench, level, true);
        is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment ench){
        ItemMeta meta = is.getItemMeta();
        assert meta != null;
        meta.removeEnchant(ench);
        is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(String... lore){
        ItemMeta meta = is.getItemMeta();
        assert meta != null;
        meta.setLore(Arrays.asList(lore));
        is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setWoolColor(DyeColor color){
        if (!is.getType().equals(Material.WHITE_WOOL))
            return this;
        this.is.setType(Material.valueOf(color.name() + "_WOOL"));
        return this;
    }

    public ItemBuilder setLeatherArmorColor(Color color){
        LeatherArmorMeta meta = (LeatherArmorMeta) is.getItemMeta();
        assert meta != null;
        meta.setColor(color);
        is.setItemMeta(meta);
        return this;
    }

    public ItemStack toItemStack(){
        return is;
    }
}