/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.muz.simpledungeon.items.potions;

import com.muz.simpledungeon.Dungeon;
import com.muz.simpledungeon.actors.buffs.Bleeding;
import com.muz.simpledungeon.actors.buffs.Buff;
import com.muz.simpledungeon.actors.buffs.Cripple;
import com.muz.simpledungeon.actors.buffs.Poison;
import com.muz.simpledungeon.actors.buffs.Weakness;
import com.muz.simpledungeon.actors.hero.Hero;
import com.muz.simpledungeon.effects.Speck;
import com.muz.simpledungeon.utils.GLog;

public class PotionOfHealing extends Potion {

	{
		name = "Potion of Healing";
	}
	
	@Override
	protected void apply( Hero hero ) {
		setKnown();
		heal( Dungeon.hero );
		GLog.p( "Your wounds heal completely." );
	}
	
	public static void heal( Hero hero ) {
		
		hero.HP = hero.HT;
		Buff.detach( hero, Poison.class );
		Buff.detach( hero, Cripple.class );
		Buff.detach( hero, Weakness.class );
		Buff.detach( hero, Bleeding.class );
		
		hero.sprite.emitter().start( Speck.factory( Speck.HEALING ), 0.4f, 4 );
	}
	
	@Override
	public String desc() {
		return
			"An elixir that will instantly return you to full health and cure poison.";
	}
	
	@Override
	public int price() {
		return isKnown() ? 30 * quantity : super.price();
	}
}
