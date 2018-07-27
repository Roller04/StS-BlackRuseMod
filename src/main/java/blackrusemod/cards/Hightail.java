package blackrusemod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import basemod.abstracts.CustomCard;
import blackrusemod.BlackRuseMod;
import blackrusemod.actions.BacklashAction;
import blackrusemod.patches.AbstractCardEnum;
import blackrusemod.powers.ProtectionPower;

public class Hightail extends CustomCard {
	public static final String ID = "Hightail";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = -1;
	private static final int PROTECTION = 6;

	public Hightail() {
		super(ID, NAME, BlackRuseMod.makePath(BlackRuseMod.HIGHTAIL), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.SILVER, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
		this.magicNumber = this.baseMagicNumber = PROTECTION;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		if (this.energyOnUse < EnergyPanel.totalCount) {
			this.energyOnUse = EnergyPanel.totalCount;
		}
		if (p.hasRelic("Chemical X")) p.getRelic("Chemical X").flash();
		for (int i = 0; i < this.energyOnUse; i++) 
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ProtectionPower(p, this.magicNumber), this.magicNumber));
		if (this.energyOnUse != 0) AbstractDungeon.actionManager.addToBottom(new BacklashAction(this.energyOnUse));
		AbstractDungeon.actionManager.addToBottom(new LoseEnergyAction(this.energyOnUse));
	}
	
	public void applyPowers() {
		this.magicNumber = this.baseMagicNumber = PROTECTION;
		if (!this.canUpgrade()) {this.magicNumber += 2; this.baseMagicNumber += 2;}
		if (AbstractDungeon.player.hasRelic("Chemical X")) {this.magicNumber += 2; this.baseMagicNumber += 2;}
		super.applyPowers();
	}

	public AbstractCard makeCopy() {
		return new Hightail();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(2);
		}
	}
}