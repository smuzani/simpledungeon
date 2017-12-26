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
package com.muz.simpledungeon.levels.traps;

import com.watabou.noosa.Camera;
import com.muz.simpledungeon.Dungeon;
import com.muz.simpledungeon.ResultDescriptions;
import com.muz.simpledungeon.actors.Char;
import com.muz.simpledungeon.actors.hero.Hero;
import com.muz.simpledungeon.effects.CellEmitter;
import com.muz.simpledungeon.effects.Lightning;
import com.muz.simpledungeon.effects.particles.SparkParticle;
import com.muz.simpledungeon.levels.Level;
import com.muz.simpledungeon.utils.GLog;
import com.muz.simpledungeon.utils.Utils;
import com.watabou.utils.Random;

public class LightningTrap {

	private static final String name	= "lightning trap";
	
	// 00x66CCEE
	
	public static void trigger( int pos, Char ch ) {
		
		if (ch != null) {
			ch.damage( Math.max( 1, Random.Int( ch.HP / 3, 2 * ch.HP / 3 ) ), LIGHTNING );
			if (ch == Dungeon.hero) {
				
				Camera.main.shake( 2, 0.3f );
				
				if (!ch.isAlive()) {
					Dungeon.fail( Utils.format( ResultDescriptions.TRAP, name, Dungeon.depth ) );
					GLog.n( "You were killed by a discharge of a lightning trap..." );
				} else {
					((Hero)ch).belongings.charge( false );
				}
			}
			
			int[] points = new int[2];
			
			points[0] = pos - Level.WIDTH;
			points[1] = pos + Level.WIDTH;
			ch.sprite.parent.add( new Lightning( points, 2, null ) );
			
			points[0] = pos - 1;
			points[1] = pos + 1;
			ch.sprite.parent.add( new Lightning( points, 2, null ) );
		}
		
		CellEmitter.center( pos ).burst( SparkParticle.FACTORY, Random.IntRange( 3, 4 ) );
		
	}
	
	public static final Electricity LIGHTNING = new Electricity();
	public static class Electricity {	
	}
}
